package memberSearch.memberSearch.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {
    private static final String[] whiltelist = {"/", "/save*", "/session*", "/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("login check filter {}", requestURI);

            if(!PatternMatchUtils.simpleMatch(whiltelist, requestURI)){
                log.info("login check logic start {} ", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute("loginMember") == null){
                    log.info("not login member {} " , requestURI);

                    httpResponse.sendRedirect("/session3?redirectURL=" + requestURI);
                    return;
                }
            }

            chain.doFilter(request, response);
        }catch (Exception e){
            throw e;
        }finally {
            log.info("login check filter end {}", requestURI);
        }


    }
}
