package kakaopay.kakaopay.api;

import kakaopay.kakaopay.dto.MemberDto;
import kakaopay.kakaopay.entitiy.Member;
import kakaopay.kakaopay.service.MemberService;
import kakaopay.kakaopay.utils.ResponseMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static kakaopay.kakaopay.entitiy.Member.createMember;
import static kakaopay.kakaopay.utils.DefaultRes.createDefaultRes;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/join-member")
    public ResponseEntity saveMember(@RequestBody @Valid CreateMemberRequest request) {
        Member member = createMember(request.getUserName(), request.getAge());
        Member joinMember = memberService.join(member);
        MemberDto memerDto = new MemberDto(joinMember.getMemberId(), joinMember.getUserName(), joinMember.getAge(), joinMember.getRegDate());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", memerDto), HttpStatus.OK);
    }

    @GetMapping("/api/find-members")
    public ResponseEntity<Object> findMembers() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> members = findMembers.stream()
                .map(o -> new MemberDto(o.getMemberId(), o.getUserName(), o.getAge(), o.getRegDate()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", members), HttpStatus.OK);
    }

    @Data
    static class CreateMemberRequest {
        private String userName;
        private int age;
    }
}


