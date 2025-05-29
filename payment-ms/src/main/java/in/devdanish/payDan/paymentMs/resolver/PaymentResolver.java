package in.devdanish.payDan.paymentMs.resolver;

import graphql.ErrorType;
import graphql.schema.DataFetchingEnvironment;
import in.devdanish.payDan.paymentMs.exception.MissingWalletException;
import in.devdanish.payDan.paymentMs.model.Transaction;
import in.devdanish.payDan.paymentMs.model.Wallet;
import in.devdanish.payDan.paymentMs.publisher.TransactionPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins= {"http://localhost:63342/"})
@Controller
public class PaymentResolver {

    @Autowired
    TransactionPublisher transactionPublisher;

    HashMap<String, List<Wallet>> userWallets = new HashMap<>();
    HashMap<String, List<Transaction>> walletTransactions = new HashMap<>();

    private static int newTransactionId = 1001;
    private static int newWalletId = 101;

    @QueryMapping
    public List<Wallet> getWallet(@Argument String userId) {
        return userWallets.get(userId);
    }

    @QueryMapping
    public List<Transaction> getTransactions(@Argument String walletId) {
        return walletTransactions.get(walletId);
    }

    @MutationMapping
    public Transaction makePayment(@Argument String debitWalletId, @Argument String creditWalletId,
                                   @Argument Double amount, @Argument String note, DataFetchingEnvironment env) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTimestamp(Instant.now().toString());
        transaction.setId("t-" + newTransactionId++);
        transaction.setDebitWalletId(debitWalletId);
        transaction.setCreditWalletId(creditWalletId);
        transaction.setNote(note);
        transaction.setStatus(Transaction.TRANSACTION_STATUS.PROCESSING);
        Wallet debitWallet = userWallets.values().stream().flatMap(List::stream).filter(wallet -> wallet.getId().equals(debitWalletId)).findFirst().orElse(null);
        Wallet creditWallet = userWallets.values().stream().flatMap(List::stream).filter(wallet -> wallet.getId().equals(creditWalletId)).findFirst().orElse(null);

        if(debitWallet == null || creditWallet == null){
            transaction.setStatus(Transaction.TRANSACTION_STATUS.FAILED);
        } else {
            try {

                if (amount <= 0) {
                    throw new RuntimeException("Invalid transaction amount");
                }

                if (debitWallet.getBalance() < amount) {
                    throw new RuntimeException("Insufficient funds");
                }

                debitWallet.setBalance(debitWallet.getBalance() - amount);
                creditWallet.setBalance(creditWallet.getBalance() + amount);

                transaction.setStatus(Transaction.TRANSACTION_STATUS.SUCESS);

            } catch (Exception e) {
                transaction.setError(e.getMessage());
                transaction.setStatus(Transaction.TRANSACTION_STATUS.FAILED);
            }
        }

        List<Transaction> debitWalletTransactions = walletTransactions.computeIfAbsent(debitWalletId, k -> new ArrayList<Transaction>());
        debitWalletTransactions.add(transaction);
        walletTransactions.put(debitWalletId, debitWalletTransactions);

        List<Transaction> creditWalletTransactions = walletTransactions.computeIfAbsent(creditWalletId, k -> new ArrayList<>());
        creditWalletTransactions.add(transaction);
        walletTransactions.put(creditWalletId, creditWalletTransactions);

        if (creditWallet == null) {
            throw new MissingWalletException(creditWalletId, List.of(env.getField().getSourceLocation()), ErrorType.ValidationError);
        } else if (debitWallet == null) {
            throw new MissingWalletException(debitWalletId, List.of(env.getField().getSourceLocation()), ErrorType.ValidationError);
        }

        transactionPublisher.publish(creditWalletId, transaction);
        transactionPublisher.publish(debitWalletId, transaction);

        return transaction;
    }

    @MutationMapping
    public Wallet addWallet(@Argument String userId, @Argument Double balance) {
        List<Wallet> wallets = userWallets.computeIfAbsent(userId, k -> new ArrayList<>());
        Wallet newWallet = new Wallet("w-" + newWalletId++, userId, balance);
        wallets.add(newWallet);
        userWallets.put(userId, wallets);
        return newWallet;
    }

    @MutationMapping
    public Transaction makePaymentAlt(@Argument Transaction transaction, DataFetchingEnvironment env){
        return makePayment(transaction.getDebitWalletId(), transaction.getCreditWalletId(), transaction.getAmount(), transaction.getNote(), env);
    }

}
