package kakaopay.kakaopay.entitiy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountHistory {

    @Id @GeneratedValue
    @Column(name = "seq")
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountNo")
    private Account account;

    @Column(name = "deposit_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DepositStatus status_YN;

    @Column(name = "deposit_amount", nullable = false)
    private int depositAmount;
    @Column(name = "deposit_date", nullable = false)
    private LocalDate depositDate;

    // 초기값 덤프 데이터 값셋팅
    public static AccountHistory initData(List<String> records, Account account) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        AccountHistory accountHistory = new AccountHistory();
        DepositStatus status_YN = DepositStatus.valueOf(records.get(1).trim());
        int depositAmount = Integer.parseInt(records.get(2).trim());
        LocalDate depositDate = LocalDate.parse(records.get(3).trim(), formatter);
        accountHistory.changeAccount(account, status_YN, depositAmount, depositDate);

        return accountHistory;
    }

    // 데이터 변경
    void changeAccount(Account account, DepositStatus depositStatus, int depositAmount, LocalDate depositDate) {
        this.account = account;
        this.status_YN = depositStatus;
        this.depositAmount = depositAmount;
        this.depositDate = depositDate;
    }

    //계좌내역 생성
    public static AccountHistory createAccountHistory(Account account, DepositStatus status_YN, int depositAmount) {
        LocalDate depositDate = LocalDate.now();
        AccountHistory accountHistory = new AccountHistory();
        accountHistory.changeAccount(account, status_YN, depositAmount, depositDate);
        return accountHistory;
    }



}
