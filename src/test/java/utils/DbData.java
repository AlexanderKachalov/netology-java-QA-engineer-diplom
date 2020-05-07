package utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbData {
    private String id;
    private String bank_id;
    private LocalDateTime created;
    private String status;
    private int amount;
    private String transaction_id;
}
