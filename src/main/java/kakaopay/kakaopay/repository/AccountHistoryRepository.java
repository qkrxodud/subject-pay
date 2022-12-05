package kakaopay.kakaopay.repository;

import kakaopay.kakaopay.entitiy.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Integer> {

    //사용자 나이대 별 평균 예치금
    @Query(value =
            "SELECT CASE " +
                   "WHEN age < 20 THEN 10 WHEN age < 30 THEN 20 WHEN age < 40 THEN 30 " +
                   "WHEN age < 50 THEN 40 WHEN age < 60 THEN 50 WHEN age < 70 THEN 60 " +
                   "WHEN age < 80 THEN 70 WHEN age < 90 THEN 80 WHEN age < 100 THEN 90" +
                   "WHEN age < 110 THEN 100 ELSE 0 END AS age_group " +
                   ", FLOOR(AVG(MBR_DEP.DEPOSIT)) AS DEPOSIT " +
              "FROM (SELECT MBR.member_id" +
                         ", MBR.age" +
                         ", MBR_DP.plus_deposit_amount - MBR_DP.minus_deposit_amount AS DEPOSIT " +
                      "FROM member AS MBR " +
                "LEFT OUTER JOIN (SELECT ACC.member_id" +
                                      ", IFNULL(Sum(AH.plus_deposit_amount), 0)   AS PLUS_DEPOSIT_AMOUNT" +
                                      ", IFNULL(Sum(AH2.minus_deposit_amount), 0) AS MINUS_DEPOSIT_AMOUNT " +
                                   "FROM account AS ACC " +
                             "LEFT OUTER JOIN (SELECT account_no, Sum(deposit_amount) AS PLUS_DEPOSIT_AMOUNT " +
                                                "FROM account_history " +
                                               "WHERE deposit_status = 'Y' " +
                                               "GROUP BY account_no) AS AH ON ACC.account_no = AH.account_no " +
                             "LEFT OUTER JOIN (SELECT account_no, Sum(deposit_amount) AS MINUS_DEPOSIT_AMOUNT " +
                                                "FROM account_history " +
                                               "WHERE deposit_status = 'N' " +
                                               "GROUP BY account_no) AS AH2 ON ACC.account_no = AH2 .account_no " +
                                  "GROUP  BY member_id) AS MBR_DP ON MBR.member_id = MBR_DP.member_id ) MBR_DEP " +
            " GROUP BY age_group ",
            nativeQuery = true)
    List<Object[]> findAgeDepositAvg();

    //년도별 예치금액
    @Query(value =
           "SELECT DPYEAR.year  " +
                ", (PLUDP .PLUS_DEPOSIT_AMOUNT - MINDP.MINUS_DEPOSIT_AMOUNT) AS DEPOSIT " +
             "FROM (SELECT FORMATDATETIME(deposit_date, 'yyyy') AS year " +
                     "FROM account_history  " +
                    "GROUP BY year) AS DPYEAR   " +
             "LEFT OUTER JOIN (SELECT FORMATDATETIME(deposit_date, 'yyyy') AS year " +
                                   ", IFNULL(Sum(deposit_amount), 0) AS MINUS_DEPOSIT_AMOUNT " +
                                "FROM account_history  " +
                               "WHERE deposit_status = 'N'  " +
                               "GROUP BY year) as MINDP ON DPYEAR.year = MINDP.year " +
             "LEFT OUTER JOIN (SELECT FORMATDATETIME(deposit_date, 'yyyy') AS year  " +
                                   ", IFNULL(Sum(deposit_amount), 0) AS PLUS_DEPOSIT_AMOUNT,  " +
                                "FROM account_history  " +
                               "WHERE deposit_status = 'Y'  " +
                               "GROUP BY year) AS PLUDP ON DPYEAR.year = PLUDP .year " +
            "WHERE DPYEAR.year = ? ",
            nativeQuery = true)
    List<Object[]> findYearDepositAmount(String year);

    //기간동안 많이 예치한 사용자 순
    @Query(value =
            "SELECT mem.member_id, mem.user_name, (PLUDP.deposit_amount - MINDP.deposit_amount) AS deposit,  " +
              "FROM Member mem " +
        "LEFT OUTER JOIN (SELECT mem.member_id" +
                              ", IFNULL(sum(ah.deposit_amount), 0) AS deposit_amount " +
                           "FROM ACCOUNT_HISTORY AS ah " +
                          "INNER JOIN account AS acc ON acc.account_no = ah.account_no " +
                          "INNER JOIN member AS mem ON mem.member_id = acc.member_id " +
                          "WHERE deposit_date > ?1 " +
                            "AND deposit_date < ?2 " +
                            "AND deposit_status  = 'Y' " +
                          "GROUP BY  mem.member_id) AS PLUDP ON mem.member_id = PLUDP.member_id " +
         "LEFT OUTER JOIN (SELECT mem.member_id ,IFNULL(sum(ah.deposit_amount), 0) as deposit_amount " +
                            "FROM ACCOUNT_HISTORY AS ah " +
                           "INNER JOIN account AS acc ON acc.account_no = ah.account_no " +
                           "INNER JOIN member AS mem ON mem.member_id = acc.member_id " +
                           "WHERE deposit_date > ?1" +
                           "  AND deposit_date < ?2" +
                           "  AND deposit_status  = 'N' " +
                           "GROUP BY  mem.member_id) AS MINDP ON mem.member_id = MINDP.member_id " +
             "ORDER BY deposit DESC;",
            nativeQuery = true)
    List<Object[]> findBetweenDateDeposit(LocalDate startDate, LocalDate endDate);

}
