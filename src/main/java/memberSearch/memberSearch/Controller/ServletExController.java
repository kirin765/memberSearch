package memberSearch.memberSearch.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {

    @GetMapping("/error-ex")
    public void errorEx(){
        throw new RuntimeException("예외 발생");
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException{
        response.sendError(404, "404 error!");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException{
        response.sendError(500);
    }

    @GetMapping("/error/{httpStatus}")
    public void errorHttpStatus(@PathVariable String httpStatus, HttpServletResponse response) throws IOException {
        log.info("httpStatus = {}", httpStatus);
        response.sendError(Integer.valueOf(httpStatus));
    }
}
