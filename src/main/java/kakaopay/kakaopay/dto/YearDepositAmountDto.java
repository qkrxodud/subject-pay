package kakaopay.kakaopay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearDepositAmountDto {
    private int year;
    private int depositAmount;
}
