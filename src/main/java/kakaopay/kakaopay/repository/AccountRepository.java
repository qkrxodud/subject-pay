package kakaopay.kakaopay.repository;

import kakaopay.kakaopay.entitiy.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String>, AccountRepositoryCustom {
    List<Account> findByMember_MemberId(Long memberId);

    //사용자의 계좌별 예치금
    @Query(value =
            "SELECT acc.account_no, acc.member_id, IFNULL(ah.plus_deposit_amount, 0), IFNULL(ah2.minus_deposit_amount, 0) " +
            "  FROM account AS acc " +
            "  LEFT OUTER JOIN (SELECT account_no, SUM(deposit_amount) AS plus_deposit_amount " +
                                 "FROM account_history " +
                                "WHERE  deposit_status = 'Y' " +
                                "GROUP BY account_no) AS ah ON acc.account_no = ah.account_no " +
            "  LEFT OUTER JOIN (SELECT account_no, SUM(deposit_amount) as minus_deposit_amount " +
                                 "FROM account_history " +
                                "WHERE DEPOSIT_STATUS = 'N' " +
                                "GROUP BY account_no) AS ah2 ON acc.account_no = ah2.account_no " +
            " WHERE acc.member_id = ? ",
            nativeQuery = true)
    List<Object[]> findDepositAmounts(int memberId);


}
