package in.devdanish.payDan.paymentMs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder(toBuilder = true)
public class Transaction {

    public enum TRANSACTION_STATUS {
        PROCESSING,
        FAILED,
        SUCESS
    }

    private String id;
    private String debitWalletId;
    private String creditWalletId;
    private Double amount;
    private String timestamp;
    private TRANSACTION_STATUS status;
    private String note;
    private String error;
}
