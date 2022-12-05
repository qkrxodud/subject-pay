package kakaopay.kakaopay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String userName;
    private int age;
    private LocalDate regDate;
}
