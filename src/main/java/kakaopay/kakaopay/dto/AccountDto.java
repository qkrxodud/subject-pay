package kakaopay.kakaopay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {
    private String accountNo;
    private Long memberId;
    private String userName;
}
