package memberSearch.memberSearch.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.Member;
import memberSearch.memberSearch.dto.LoginFormDto;
import memberSearch.memberSearch.service.LoginService;
import memberSearch.memberSearch.service.MemberService;
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
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;

    @GetMapping("")
    public String loginPage(Model model){
        model.addAttribute("loginFormDto", new LoginFormDto());
        return "login";
    }

    @PostMapping("")
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

        return "redirect:/";
    }
}
