package memberSearch.memberSearch.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.Member;
import memberSearch.memberSearch.repository.MemberSearchCondition;
import memberSearch.memberSearch.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public Member addMember(@RequestBody Member member) {
        memberService.saveMember(member);
        log.info("addMember: {}", member);
        return member;
    }

    @PutMapping("/{memberId}")
    public Member updateMember(@RequestBody Member member){
        memberService.updateMember(member);
        log.info("updateMember: {}", member);
        return member;
    }

    @GetMapping("/{memberId}")
    public Member getMember(@PathVariable String memberId){
        Member findMember = memberService.findMember(memberId);
        log.info("memberId={}", memberId);
        return findMember;
    }

    @GetMapping("")
    public List<Member> findAll(@PathParam("id") String id,
                                @PathParam("name") String name,
                                @PathParam("password") String password){
        log.info("id={}, name={}, password={}", id, name, password);
        List<Member> memberList = memberService.findAllMember(new MemberSearchCondition(id, name, password));
        return memberList;
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable String memberId){
        memberService.deleteMember(memberId);
    }
}
