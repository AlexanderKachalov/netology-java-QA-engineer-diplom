package utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbData {
    private String status;
    private int amount;
    private String transaction_id;
    private String bank_id;
    private String payment_id;
    private String credit_id;
}
