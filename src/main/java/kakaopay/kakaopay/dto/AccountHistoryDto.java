package kakaopay.kakaopay.dto;

import kakaopay.kakaopay.entitiy.DepositStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AccountHistoryDto {
    private String accountNo;
    private int depositAmount;
    private DepositStatus status_YN;
    private LocalDate depositDate;
}
