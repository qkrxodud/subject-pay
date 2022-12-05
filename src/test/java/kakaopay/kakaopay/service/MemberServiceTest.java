package kakaopay.kakaopay.service;

import kakaopay.kakaopay.entitiy.Member;
import kakaopay.kakaopay.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional

class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //given
        Member member = Member.createMember("typark", 40);
        //when
        Member joinMember = memberService.join(member);
        //then
        assertThat(member.getMemberId()).isEqualTo(joinMember.getMemberId());

    }

    @Test
    public void 회원이름중복체크(){
        //given
        Member member = Member.createMember("typark", 40);
        memberService.join(member);

        //then
        assertThatIllegalArgumentException().isThrownBy(()->{
            memberService.join(member);
        });
    }

    @Test
    public void 전체회원조회() {
        //when
        List<Member> members = memberService.findMembers();
        //then
        assertThat(members.size()).isEqualTo(20);
    }

    @Test
    public void 회원아이디로조회() {
        //given
        Optional<Member> findMember = memberService.findById(1L);

        //when //then
        assertThat(findMember.get().getUserName()).isEqualTo("Liam");
    }

    @Test
    public void 회원이름으로조회() {
        //given
        List<Member> findMember = memberService.findByUserName("Liam");

        //when//then
        assertThat(findMember.get(0).getMemberId()).isEqualTo(1L);
    }




}