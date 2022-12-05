package kakaopay.kakaopay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DateDepositAmountDto {
    private String memberId;
    private String userName;
    private int deposit;
}
