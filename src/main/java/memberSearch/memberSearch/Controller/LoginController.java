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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.*;

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

    @GetMapping("/session3")
    public String loginSessionPage3(Model model){
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

    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("id", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
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

    @PostMapping("/sessionlogout")
    public String sessionLogout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        log.info("sessionLogout cookies = {}", cookies);
        Cookie cookie1 = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("mySessionId"))
                .findAny()
                .orElse(null);

        log.info("sessionlogout cookie = {}", cookie1);
        if(cookie1 == null)
            return "redirect:/";

        String mySessionId = cookie1.getValue();
        sessionService.delete(mySessionId);
        Cookie expiredCookie = new Cookie("mySessionId", null);
        expiredCookie.setMaxAge(0);
        response.addCookie(expiredCookie);
        return "redirect:/";
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

    @PostMapping("/sessionlogout2")
    public String sessionLogout2(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        log.info("session={}", session);
        if(session == null)
            return "redirect:/";

        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/session3")
    public String loginSession3(@ModelAttribute @Valid LoginFormDto loginFormDto,
                                BindingResult bindingResult,
                                @PathParam("redirectURL") String redirectURL,
                                HttpServletRequest request){
        Member loginMember = null;
        log.info("loginSession3 requestURL = {}", redirectURL);
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

        return "redirect:" + redirectURL;
    }
}
