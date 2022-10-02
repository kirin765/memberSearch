package memberSearch.memberSearch.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.argumentresolver.Login;
import memberSearch.memberSearch.domain.Member;
import memberSearch.memberSearch.domain.MySession;
import memberSearch.memberSearch.repository.MemberSearchCondition;
import memberSearch.memberSearch.service.MemberService;
import memberSearch.memberSearch.service.SessionService;
import memberSearch.memberSearch.validator.MemberValidator;
import memberSearch.memberSearch.validator.UpdateCheck;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class ViewController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;
    private final SessionService sessionService;

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

    @GetMapping("/loginhome")
    public String loginHome(@CookieValue(name="id", required = false) String id, Model model){
        List<String> links = new ArrayList<>();
        links.add("/save");
        links.add("/spec");
        links.add("/find");
        model.addAttribute("links", links);

        if(id==null){
            return "home";
        }

        Member loginMember = null;

        try {
            loginMember = memberService.findMember(id);
        }catch (NoSuchElementException e){
            log.error("no such member");
            return "home";
        }

        model.addAttribute("loginmember", loginMember);

        return "loginhome";
    }

    @GetMapping("/loginSessionHome")
    public String loginSessionHome(HttpServletRequest request, Model model){
        List<String> links = new ArrayList<>();
        links.add("/save");
        links.add("/spec");
        links.add("/find");
        model.addAttribute("links", links);

        Cookie[] cookies = request.getCookies();

        log.info("cookies={}", cookies);

        if(cookies == null)
            return "home";

        Cookie sessionCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("mySessionId"))
                .findAny()
                .orElse(null);

        if(sessionCookie==null)
            return "home";

        String mySessionId = sessionCookie.getValue();

        if(mySessionId==null){
            return "home";
        }

        Member loginMember = null;
        String id = sessionService.getId(mySessionId);

        if(id==null){
            log.info("세션 만료");
            return "home";
        }

        try {
            loginMember = memberService.findMember(id);
        }catch (NoSuchElementException e){
            log.error("no such member");
            return "home";
        }

        MySession newSession = new MySession(mySessionId, id, LocalDateTime.now());
        sessionService.updateCreatedBy(newSession);

        model.addAttribute("loginmember", loginMember);

        log.info("mySessionId={}, member={}", mySessionId, loginMember);

        return "loginhome";
    }


    @GetMapping("/loginSessionHome2")
    public String loginSessionHome2(HttpServletRequest request, Model model){
        List<String> links = new ArrayList<>();
        links.add("/save");
        links.add("/spec");
        links.add("/find");
        model.addAttribute("links", links);

        HttpSession session = request.getSession(false);

        if(session==null){
            return "home";
        }

        Member loginMember = (Member) session.getAttribute("loginMember");

        model.addAttribute("loginmember", loginMember);

        return "loginhome";
    }

    @GetMapping("/loginSessionHome3")
    public String loginSessionHome3(@SessionAttribute(name = "loginMember", required = false) Member member, Model model){
        List<String> links = new ArrayList<>();
        links.add("/save");
        links.add("/spec");
        links.add("/find");
        model.addAttribute("links", links);

        if(member==null){
            return "home";
        }

        model.addAttribute("loginmember", member);
        log.info("member={}", member);

        return "loginhome";
    }

    @GetMapping("/loginSessionHome4")
    public String loginSessionHome4(@Login Member member, Model model){
        List<String> links = new ArrayList<>();
        links.add("/save");
        links.add("/spec");
        links.add("/find");
        model.addAttribute("links", links);

        if(member==null){
            return "home";
        }

        model.addAttribute("loginmember", member);
        log.info("member={}", member);

        return "loginhome";
    }


    @GetMapping("/save")
    public String save(Model model){
        model.addAttribute("member", new Member());
        return "save";
    }

    @GetMapping("/edit/{memberId}")
    public String edit(@PathVariable("memberId") String id, Model model){
        Member findMember = memberService.findMember(id);
        log.info("id={}", id);
        model.addAttribute("member", findMember);
        return "edit";
    }

    @PostMapping("/edit")
    public String editMember(@ModelAttribute @Validated(UpdateCheck.class) Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        redirectAttributes.addAttribute("memberId", member.getId());

        if(bindingResult.hasErrors()){
            log.info("error={}", bindingResult);
            return "redirect:/find";
        }

        memberService.updateMember(member);

        return "redirect:/spec/{memberId}";
    }

    @GetMapping("/save2")
    public String save2(Model model){
        model.addAttribute("member", new Member());
        return "save2";
    }

    @GetMapping("/save3")
    public String save3(Model model){
        model.addAttribute("member", new Member());
        return "save3";
    }

    @GetMapping("/save4")
    public String save4(Model model){
        model.addAttribute("member", new Member());
        return "save4";
    }

    @GetMapping("/save5")
    public String save5(Model model){
        model.addAttribute("member", new Member());
        return "save5";
    }

    @GetMapping("/save6")
    public String save6(Model model){
        model.addAttribute("member", new Member());
        return "save6";
    }

    @GetMapping("/save7")
    public String save7(Model model){
        model.addAttribute("member", new Member());
        return "save7";
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

    @PostMapping("/save2")
    public String saveMember2(@ModelAttribute Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(!StringUtils.hasText(member.getId())){
            bindingResult.addError(new FieldError("member", "id", "회원 아이디를 입력해주세요"));
        }

        if(!StringUtils.hasText(member.getName())){
            bindingResult.addError(new FieldError("member", "name", "회원 이름을 입력해주세요"));
        }

        if(!StringUtils.hasText(member.getPassword())){
            bindingResult.addError(new FieldError("member", "password", "비밀번호를 입력해주세요"));
        }

        if(!StringUtils.hasText(member.getId()) &&
        !StringUtils.hasText(member.getName()) &&
        !StringUtils.hasText(member.getPassword())){
            bindingResult.addError(new ObjectError("member", "회원정보를 입력해주세요"));
        }

        if(bindingResult.hasErrors()){
            log.info("errors={} ", bindingResult);
            return "save2";
        }

        log.info("save success");

        memberService.saveMember(member);
        redirectAttributes.addAttribute("memberId", member.getId());

        return "redirect:/spec/{memberId}";
    }

    @PostMapping("/save3")
    public String saveMember3(@ModelAttribute Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(!StringUtils.hasText(member.getId())){
            bindingResult.addError(new FieldError("member",
                    "id",
                    member.getId(),
                    false,
                    null,
                    null,
                    "회원 아이디를 입력해주세요123"));
        }

        if(!StringUtils.hasText(member.getName())){
            bindingResult.addError(new FieldError("member",
                    "name",
                    member.getName(),
                    false,
                    null,
                    null,
                    "회원 이름을 입력해주세요"));
        }

        if(StringUtils.hasText(member.getName()) && member.getName().length() > 10){
            bindingResult.addError(new FieldError("member",
                    "name",
                    member.getName(),
                    false,
                    null,
                    null,
                    "회원 이름은 10자 이하"));
        }

        if(!StringUtils.hasText(member.getPassword())){
            bindingResult.addError(new FieldError("member", "password", "비밀번호를 입력해주세요"));
        }

        if(!StringUtils.hasText(member.getId()) &&
                !StringUtils.hasText(member.getName()) &&
                !StringUtils.hasText(member.getPassword())){
            bindingResult.addError(new ObjectError("member", "회원정보를 입력해주세요"));
        }

        if(bindingResult.hasErrors()){
            log.info("errors={} ", bindingResult);
            return "save3";
        }

        log.info("save success");

        memberService.saveMember(member);
        redirectAttributes.addAttribute("memberId", member.getId());

        return "redirect:/spec/{memberId}";
    }


    @PostMapping("/save4")
    public String saveMember4(@ModelAttribute Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(!StringUtils.hasText(member.getId())){
            bindingResult.addError(new FieldError("member",
                    "id",
                    member.getId(),
                    false,
                    new String[]{"required.member.id"},
                    null,
                    null));
        }

        if(!StringUtils.hasText(member.getName())){
            bindingResult.addError(new FieldError("member",
                    "name",
                    member.getName(),
                    false,
                    new String[]{"required.member.name"},
                    null,
                    null));
        }

        if(StringUtils.hasText(member.getName()) && member.getName().length() > 10){
            bindingResult.addError(new FieldError("member",
                    "name",
                    member.getName(),
                    false,
                    new String[]{"length.member.name"},
                    new Object[]{1, 10},
                    null));
        }

        if(!StringUtils.hasText(member.getPassword())){
            bindingResult.addError(new FieldError(
                    "member",
                    "password",
                    member.getPassword(),
                    false,
                    new String[]{"required.member.password"},
                    null,
                    null
                    ));
        }

        if(!StringUtils.hasText(member.getId()) &&
                !StringUtils.hasText(member.getName()) &&
                !StringUtils.hasText(member.getPassword())){
            bindingResult.addError(new ObjectError("member",
                    new String[]{"required.member"},
                    null,
                    null
                    ));
        }

        if(bindingResult.hasErrors()){
            log.info("errors={} ", bindingResult);
            return "save4";
        }

        log.info("save success");

        memberService.saveMember(member);
        redirectAttributes.addAttribute("memberId", member.getId());

        return "redirect:/spec/{memberId}";
    }


    @PostMapping("/save5")
    public String saveMember5(@ModelAttribute Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());

        if(!StringUtils.hasText(member.getId())){
            bindingResult.rejectValue("id", "required");
        }

        if(!StringUtils.hasText(member.getName())){
            bindingResult.rejectValue("name", "required");
        }

        if(StringUtils.hasText(member.getName()) && member.getName().length() > 10){
            bindingResult.rejectValue("name", "length", new Object[]{1,10}, null);
        }

        if(!StringUtils.hasText(member.getPassword())){
            bindingResult.rejectValue("password", "required");
        }

        if(!StringUtils.hasText(member.getId()) &&
                !StringUtils.hasText(member.getName()) &&
                !StringUtils.hasText(member.getPassword())){
            bindingResult.reject("required");
        }

        if(bindingResult.hasErrors()){
            log.info("errors={} ", bindingResult);
            return "save5";
        }

        log.info("save success");

        memberService.saveMember(member);
        redirectAttributes.addAttribute("memberId", member.getId());

        return "redirect:/spec/{memberId}";
    }

    @PostMapping("/save6")
    public String saveMember6(@ModelAttribute Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        memberValidator.validate(member, bindingResult);

        if(bindingResult.hasErrors()){
            log.info("errors={} ", bindingResult);
            return "save6";
        }

        log.info("save success");

        memberService.saveMember(member);
        redirectAttributes.addAttribute("memberId", member.getId());

        return "redirect:/spec/{memberId}";
    }

    @PostMapping("/save7")
    public String saveMember7(@ModelAttribute @Validated Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(bindingResult.hasErrors()){
            log.info("errors={} ", bindingResult);
            return "save7";
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
