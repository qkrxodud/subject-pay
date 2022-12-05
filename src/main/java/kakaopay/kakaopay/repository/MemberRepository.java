package kakaopay.kakaopay.repository;

import kakaopay.kakaopay.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUserName(String userName);
}
