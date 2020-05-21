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
    private String transactionId;
    private String bankId;
    private String paymentId;
    private String creditId;
    
}
