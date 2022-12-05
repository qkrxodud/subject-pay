package kakaopay.kakaopay.service;

import kakaopay.kakaopay.entitiy.AccountHistory;
import kakaopay.kakaopay.repository.AccountHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static kakaopay.kakaopay.utils.Util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountHistoryService {

    private final AccountHistoryRepository accountHistoryRepository;

    //계좌내역 추가
    @Transactional
    public AccountHistory saveAccountHistory(AccountHistory accountHistory) {
        return accountHistoryRepository.save(accountHistory);
    }

    //전체 계좌 조회
    public List<AccountHistory> findAccounts() {
        return accountHistoryRepository.findAll();
    }

    //나이대별 평균 예치금 조회
    public List<Object[]> findAgeDepositAvg() {
        return accountHistoryRepository.findAgeDepositAvg();
    }

    //연도별 예치금 조회
    public List<Object[]> findYearDepositAmount(String year) {
        if (!validationYearCheck(year)) {
            throw new IllegalStateException("잘못된 날짜 형식입니다.");
        }
        return accountHistoryRepository.findYearDepositAmount(year);
    }

    //기간별 사용자별 예치금 조회
    public List<Object[]> findBetweenDateDeposit(String startDate, String endDate) {
        if (!validationCheck(startDate) || !validationCheck(endDate)) {
            throw new IllegalStateException("잘못된 날짜 형식입니다.yyyyMMdd 형식으로 작성해주세요.");
        }
        String startDateFormat = returnDateForMat(startDate);
        String endDateFormat = returnDateForMat(endDate);

        DateTimeFormatter startFormatter = DateTimeFormatter.ofPattern(startDateFormat);
        DateTimeFormatter endFormatter = DateTimeFormatter.ofPattern(endDateFormat);

        LocalDate startDateVal = LocalDate.parse(startDate, startFormatter);
        LocalDate endDateVal = LocalDate.parse(endDate, endFormatter);

        return accountHistoryRepository.findBetweenDateDeposit(startDateVal, endDateVal);
    }
}
