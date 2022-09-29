package memberSearch.memberSearch.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.Member;
import memberSearch.memberSearch.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class ViewController {

    private final MemberService memberService;

    @GetMapping("")
    public String home(Model model){
        List<String> links = new ArrayList<>();
        links.add("/save");
        links.add("/spec");
        links.add("/find");
        model.addAttribute("links", links);
        return "home";
    }

    @GetMapping("/save")
    public String save(Model model){
        model.addAttribute("member", new Member());
        return "save";
    }

    @PostMapping("/save")
    public String saveMember(@ModelAttribute Member member, RedirectAttributes redirectAttributes, Model model){
        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(member.getId())){
            errors.put("id", "회원 아이디를 입력해주세요");
        }

        if(!StringUtils.hasText(member.getName())){
            errors.put("name", "회원 이름을 입력해주세요");
        }

        if(!StringUtils.hasText(member.getPassword())){
            errors.put("password", "비밀번호를 입력해주세요");
        }

        if(!errors.isEmpty()){
            model.addAttribute("errors", errors);

            log.info("save fail");
            return "save";
        }

        log.info("save success");

        memberService.saveMember(member);
        redirectAttributes.addAttribute("memberId", member.getId());

        return "redirect:/spec/{memberId}";
    }

    @GetMapping("/spec/{memberId}")
    public String member(@PathVariable String memberId, Model model){
        Member findMember = memberService.findMember(memberId);
        model.addAttribute("member", findMember);
        return "member";
    }

    @GetMapping("/find")
    public String find(@PathParam("id") String id,
                       @PathParam("name") String name,
                       @PathParam("password") String password, Model model){

        if(StringUtils.hasText(id)){
            model.addAttribute("");
        }
        log.info("id={}, name={}, password={}", id, name, password);



        return "findMember";
    }
}
