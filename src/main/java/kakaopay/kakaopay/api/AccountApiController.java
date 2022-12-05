package kakaopay.kakaopay.api;

import kakaopay.kakaopay.dto.AccountDepositAmountDto;
import kakaopay.kakaopay.dto.AccountDto;
import kakaopay.kakaopay.entitiy.Account;
import kakaopay.kakaopay.entitiy.Member;
import kakaopay.kakaopay.service.AccountService;
import kakaopay.kakaopay.service.MemberService;
import kakaopay.kakaopay.utils.ResponseMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static kakaopay.kakaopay.utils.DefaultRes.createDefaultRes;

@RestController
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountService accountService;
    private final MemberService memberService;

    // 계좌 생성
    @PostMapping("/api/join-account")
    public ResponseEntity saveAccount(@RequestBody @Valid CreateAccountRequest request ) {
        Member findMember = memberService.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        Account saveAccount = accountService.join(findMember);
        AccountDto accountDto = new AccountDto(saveAccount.getAccountNo(), saveAccount.getMember().getMemberId(), saveAccount.getMember().getUserName());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK, "SUCCESS", accountDto), HttpStatus.OK);
    }

    //계좌 조회
    @GetMapping("/api/find-accounts")
    public ResponseEntity<Object> findAccounts() {
        List<Account> findAccounts = accountService.findAccounts();
        List<AccountDto> accounts = findAccounts.stream()
                .map(o -> new AccountDto(o.getAccountNo(), o.getMember().getMemberId(), o.getMember().getUserName()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", accounts), HttpStatus.OK);
    }

    //예치금 조회
    @GetMapping("/api/member-deposits")
    public ResponseEntity findMemberDeposits(@RequestParam(value = "memberId", defaultValue = "0") int memberId) {
        List<Object[]> depositAmounts = accountService.findDepositAmounts(memberId);
        List<AccountDepositAmountDto> findDepositAmounts = depositAmounts.stream()
                .map(o -> new AccountDepositAmountDto(
                        o[0].toString(),
                        Integer.parseInt(o[1].toString()),
                        Integer.parseInt(o[2].toString()) - Integer.parseInt(o[3].toString())
                        )).collect(Collectors.toList());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", findDepositAmounts), HttpStatus.OK);
    }

    @Data
    static class CreateAccountRequest {
        private Long memberId;
    }
}
