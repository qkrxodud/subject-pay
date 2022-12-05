package kakaopay.kakaopay.service;

import kakaopay.kakaopay.entitiy.Account;
import kakaopay.kakaopay.entitiy.Member;
import kakaopay.kakaopay.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MemberService memberService;

    @Test
    public void 계좌가입() throws Exception {
        //given
        Member member = Member.createMember("test", 40);
        memberService.join(member);

        //when
        Account saveAccount = accountService.join(member);

        //then
        assertThat(saveAccount.getMember()).isEqualTo(member);
    }

    @Test
    public void 전체계좌조회() {
        //when
        List<Account> members = accountService.findAccounts();
        //then
        assertThat(members.size()).isEqualTo(50);
    }

    @Test
    public void 계좌번호로조회() {
        //given
        Optional<Account> byAccount = accountService.findByAccount("1000-15");

        //when
        Account account = byAccount.get();
        Member member = account.getMember();

        //then
        assertThat("Isabella").isEqualTo(member.getUserName());
    }

   @Test
   public void 멤버이름으로조회() {
       //given
       List<Member> findMembers = memberService.findByUserName("Liam");
       Member member = findMembers.get(0);

       //when
       List<Account> findAccount = accountService.findByMemberId(member.getMemberId());

       //then
       assertThat(findAccount.size()).isEqualTo(2);
   }


    @Test
    public void 예치금조회() {
        //given
        List<Object[]> depositAmount = accountService.findDepositAmounts(2);

        //then
        assertThat(depositAmount.size()).isEqualTo(49);
    }



}