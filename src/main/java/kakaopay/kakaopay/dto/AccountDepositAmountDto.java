package kakaopay.kakaopay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDepositAmountDto {
    private String accountNo;
    private int memberId;
    private int depositAmount;
}