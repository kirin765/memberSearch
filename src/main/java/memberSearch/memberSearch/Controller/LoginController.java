package memberSearch.memberSearch.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.Member;
import memberSearch.memberSearch.domain.MySession;
import memberSearch.memberSearch.dto.LoginFormDto;
import memberSearch.memberSearch.service.LoginService;
import memberSearch.memberSearch.service.MemberService;
import memberSearch.memberSearch.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;
    private final SessionService sessionService;

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginFormDto", new LoginFormDto());
        return "login";
    }

    @GetMapping("/session")
    public String loginSessionPage(Model model){
        model.addAttribute("loginFormDto", new LoginFormDto());
        return "loginSession";
    }

    @GetMapping("/session2")
    public String loginSessionPage2(Model model){
        model.addAttribute("loginFormDto", new LoginFormDto());
        return "loginSessionV";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid LoginFormDto loginFormDto, BindingResult bindingResult, HttpServletResponse response, Model model){
        Member loginMember = null;
        try {
            loginMember = loginService.login(loginFormDto.getId(), loginFormDto.getPassword());
        }catch (NoSuchElementException e){
            bindingResult.reject("login", "login fail");
        }

        if(bindingResult.hasErrors()){
            log.error("error={}", bindingResult);
            return "login";
        }

        Cookie idCookie = new Cookie("id", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return "redirect:/loginhome";
    }

    @PostMapping("/session")
    public String loginSession(@ModelAttribute @Valid LoginFormDto loginFormDto, BindingResult bindingResult, HttpServletResponse response, Model model){
        Member loginMember = null;
        try {
            loginMember = loginService.login(loginFormDto.getId(), loginFormDto.getPassword());
        }catch (NoSuchElementException e){
            bindingResult.reject("login", "login fail");
        }

        if(bindingResult.hasErrors()){
            log.error("error={}", bindingResult);
            return "loginSession";
        }

        String uuid = UUID.randomUUID().toString();

        MySession mySession = new MySession(uuid, loginMember.getId(), LocalDateTime.now());

        sessionService.save(mySession);

        Cookie idCookie = new Cookie("mySessionId", uuid);
        response.addCookie(idCookie);

        log.info("session cookie name={}, value={}", idCookie.getName(), idCookie.getValue());

        return "redirect:/loginSessionHome";
    }

    @PostMapping("/session2")
    public String loginSession2(@ModelAttribute @Valid LoginFormDto loginFormDto, BindingResult bindingResult, HttpServletRequest request){
        Member loginMember = null;
        try {
            loginMember = loginService.login(loginFormDto.getId(), loginFormDto.getPassword());
        }catch (NoSuchElementException e){
            bindingResult.reject("login", "login fail");
        }

        if(bindingResult.hasErrors()){
            log.error("error={}", bindingResult);
            return "loginSessionV";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);

        return "redirect:/loginSessionHome2";
    }
}
