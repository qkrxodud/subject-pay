package kakaopay.kakaopay.api;

import kakaopay.kakaopay.dto.AccountHistoryDto;
import kakaopay.kakaopay.dto.AgeDepositAvgDto;
import kakaopay.kakaopay.dto.DateDepositAmountDto;
import kakaopay.kakaopay.dto.YearDepositAmountDto;
import kakaopay.kakaopay.entitiy.Account;
import kakaopay.kakaopay.entitiy.AccountHistory;
import kakaopay.kakaopay.entitiy.DepositStatus;
import kakaopay.kakaopay.service.AccountHistoryService;
import kakaopay.kakaopay.service.AccountService;
import kakaopay.kakaopay.utils.ResponseMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kakaopay.kakaopay.entitiy.AccountHistory.createAccountHistory;
import static kakaopay.kakaopay.utils.DefaultRes.createDefaultRes;

@RestController
@RequiredArgsConstructor
public class AccountHistoryApiController {

    private final AccountHistoryService accountHistoryService;
    private final AccountService accountService;

    //계좌 내역 히스토리 추가
    @PostMapping("/api/save-account-history")
    public ResponseEntity saveAccountHistory(@RequestBody @Valid CreateAccountHistoryRequest request) {
        Optional<Account> findAccount = accountService.findByAccount(request.getAccountNo());
        Account account = findAccount.orElseThrow(() -> new IllegalArgumentException("계좌 번호가 없습니다."));

        AccountHistory saveAccountHistory = accountHistoryService.saveAccountHistory(
                createAccountHistory(account, request.getStatus_YN(), request.getDepositAmount()));

        AccountHistoryDto accountHistoryDto = new AccountHistoryDto(saveAccountHistory.getAccount().getAccountNo()
                , saveAccountHistory.getDepositAmount(), saveAccountHistory.getStatus_YN(), saveAccountHistory.getDepositDate());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", accountHistoryDto), HttpStatus.OK);
    }

    //계좌 내역 히스토리 조회
    @GetMapping("/api/find-accounts-history")
    public ResponseEntity<Object> findAccountsHistory() {
        List<AccountHistory> AccountsHistory = accountHistoryService.findAccounts();

        List<AccountHistoryDto> findAccountsHistory = AccountsHistory.stream()
                .map(o -> new AccountHistoryDto(o.getAccount().getAccountNo(), o.getDepositAmount(),
                        o.getStatus_YN(), o.getDepositDate()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", findAccountsHistory), HttpStatus.OK);
    }

    //나이대별 평균 예치금 조회
    @GetMapping("/api/age-deposits-avg")
    public ResponseEntity findAgeDepositAvg() {
        List<Object[]> ageDepositAvg = accountHistoryService.findAgeDepositAvg();
        List<AgeDepositAvgDto> findMemberDepositAmounts = ageDepositAvg.stream()
                .map(o -> new AgeDepositAvgDto(
                        Integer.parseInt(o[0].toString()),
                        Integer.parseInt(o[1].toString())
                )).collect(Collectors.toList());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", findMemberDepositAmounts), HttpStatus.OK);
    }

    //연도별 예치금 조회
    @GetMapping("/api/year-deposits-amount")
    public ResponseEntity findYearDepositAmount(@RequestParam(value = "year", defaultValue = "0") String year) {
        List<Object[]> yearDepositAmount = accountHistoryService.findYearDepositAmount(year);
        List<YearDepositAmountDto> findYearDepositAmountDto = yearDepositAmount.stream()
                .map(o -> new YearDepositAmountDto(
                        Integer.parseInt(o[0].toString()),
                        Integer.parseInt(o[1].toString())
                )).collect(Collectors.toList());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", findYearDepositAmountDto), HttpStatus.OK);
    }

    //기간별 사용자별 예치금 조회
    @GetMapping("/api/between-date-deposit")
    public ResponseEntity findBetweenDateDeposit(@RequestParam(value = "startDate", defaultValue = "0") String startDate
            , @RequestParam(value = "endDate", defaultValue = "0") String endDate) {
        List<Object[]> betweenDateDeposits = accountHistoryService.findBetweenDateDeposit(startDate, endDate);
        List<DateDepositAmountDto> dateDepositAmounts = betweenDateDeposits.stream()
                .map(o -> new DateDepositAmountDto(
                        o[0].toString(),
                        o[1].toString(),
                        Integer.parseInt(o[2].toString())
                )).collect(Collectors.toList());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", dateDepositAmounts), HttpStatus.OK);
    }

    @Data
    static class CreateAccountHistoryRequest {
        private String AccountNo;
        private int depositAmount;
        private DepositStatus status_YN;
    }
}
