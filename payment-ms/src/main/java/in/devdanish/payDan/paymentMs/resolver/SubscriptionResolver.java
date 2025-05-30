package in.devdanish.payDan.paymentMs.resolver;

import in.devdanish.payDan.paymentMs.model.Transaction;
import in.devdanish.payDan.paymentMs.publisher.TransactionPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class SubscriptionResolver {
    @Autowired
    TransactionPublisher transactionPublisher;

    @SubscriptionMapping
    public Flux<Transaction> onTransactionAdded(@Argument String walletId){
        return transactionPublisher.subscribe(walletId);
    }
}
