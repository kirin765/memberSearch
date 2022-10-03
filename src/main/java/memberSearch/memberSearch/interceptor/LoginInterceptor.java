package memberSearch.memberSearch.interceptor;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.Member;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        request.setAttribute("uuid", uuid);

        log.info("REQUEST [{}][{}]", uuid, requestURI);

        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/session3?redirectURL=" + requestURI);
            return false;
        }

        Member loginMember = (Member) session.getAttribute("loginMember");

        if(loginMember == null){
            response.sendRedirect("/session3?redirectURL=" + requestURI);
            return false;
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uuid = (String) request.getAttribute("uuid");
        log.info("postHandle [{}]", uuid);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute("uuid");
        log.info("RESPONSE [{}][{}]", uuid, requestURI);
    }
}
