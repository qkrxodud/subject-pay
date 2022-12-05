package kakaopay.kakaopay.service;

import kakaopay.kakaopay.entitiy.Member;
import kakaopay.kakaopay.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Member join(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    //회원 이름 중복 체크
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByUserName(member.getUserName());
        if (!findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미존재하는 닉네임입니다.");
        }
    }

    //전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 아이디로 조회
    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //회원 이름으로 조회
    public List<Member> findByUserName(String userName) {
        return memberRepository.findByUserName(userName);
    }

}
