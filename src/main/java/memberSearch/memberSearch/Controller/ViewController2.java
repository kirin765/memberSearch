package memberSearch.memberSearch.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.Member;
import memberSearch.memberSearch.repository.MemberSearchCondition;
import memberSearch.memberSearch.service.MemberService;
import memberSearch.memberSearch.validator.MemberValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/api2")
@RequiredArgsConstructor
public class ViewController2 {

    private final MemberService memberService;
    private final MemberValidator memberValidator;

//    @InitBinder
//    public void init(WebDataBinder dataBinder){
//        dataBinder.addValidators(memberValidator);
//    }

    @GetMapping("")
    public String home(Model model){
        List<String> links = new ArrayList<>();
        links.add("/save");
        links.add("/spec");
        links.add("/find");
        model.addAttribute("links", links);
        return "home";
    }

    @GetMapping("/save8")
    public String save8(Model model){
        model.addAttribute("member", new Member());
        return "save8";
    }

    @PostMapping("/save8")
    public String saveMember8(@ModelAttribute @Validated Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(bindingResult.hasErrors()){
            log.info("errors={} ", bindingResult);
            return "save8";
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

        log.info("id={} name={} password={}", id, name, password);

        MemberSearchCondition cond = new MemberSearchCondition(id, name, password);

        List<Member> memberList = memberService.findAllMember(cond);

        model.addAttribute("members", memberList);

        Map<String, String> inputs = new HashMap<>();

        inputs.put("id", id);
        inputs.put("name", name);
        inputs.put("password", password);

        model.addAttribute("inputs", inputs);

        return "findMember";
    }
}
