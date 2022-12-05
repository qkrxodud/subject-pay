package kakaopay.kakaopay.service;

import kakaopay.kakaopay.entitiy.Account;
import kakaopay.kakaopay.entitiy.AccountHistory;
import kakaopay.kakaopay.entitiy.DepositStatus;
import kakaopay.kakaopay.entitiy.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class AccountHistoryServiceTest {
    
    @Autowired
    AccountHistoryService accountHistoryService;

    @Autowired
    AccountService accountService;

    @Autowired
    MemberService memberService;
    
    @Test
    public void 계좌내역추가() {
        //given
        List<Member> findMember = memberService.findByUserName("Liam");
        List<Account> findAccount = accountService.findByMemberId(findMember.get(0).getMemberId());
        AccountHistory accountHistory = AccountHistory.createAccountHistory(findAccount.get(0), DepositStatus.Y, 30000);

        //when
        AccountHistory saveAccountHistory = accountHistoryService.saveAccountHistory(accountHistory);
        
        //then
        Assertions.assertThat(accountHistory).isEqualTo(saveAccountHistory);
    }

    @Test
    public void 계좌내역조회() {

        //given
        List<AccountHistory> members = accountHistoryService.findAccounts();

        //then
        Assertions.assertThat(members.size()).isEqualTo(500);
    }

    @Test
    public void 나이대별평균예치금조회() {
        List<Object[]> ageDepositAvg = accountHistoryService.findAgeDepositAvg();
        Assertions.assertThat(Integer.parseInt(ageDepositAvg.get(1)[1].toString())).isEqualTo(923450);
    }

}