package kakaopay.kakaopay.entitiy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id @Column(name = "account_no", nullable = false)
    private String accountNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    public static Account initData(List<String> records, Member member) {
        Account account = new Account();
        String accountNo = records.get(1).trim();
        account.changeAccount(accountNo, member);
        return account;
    }

    public static Account createAccount(String accountNo, Member member) {
        Account account = new Account();
        account.changeAccount(accountNo, member);
        return account;
    }

    void changeAccount(String accountNo, Member member) {
        this.accountNo = accountNo;
        this.member = member;
    }

    public static String createAccountNo(String preMaxValue, int bankCode) {
        int maxIndex = Integer.parseInt(preMaxValue.substring(5));
        return bankCode + "-" + (++maxIndex);
    }
}
