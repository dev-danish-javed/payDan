package in.devdanish.payDan.paymentMs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    private String id;
    private String userId;
    private Double balance;
}
