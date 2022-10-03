package memberSearch.memberSearch.Controller;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.IpPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class HelloController {

    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request){
        String data = request.getParameter("data");
        Integer integer = Integer.valueOf(data);
        System.out.println("integer = " + integer);
        return "ok";
    }

    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data){
        System.out.println("data = " + data);
        return "ok";
    }

    @GetMapping("/hello-v3")
    public String helloV3(@RequestParam IpPort ipPort){
        System.out.println("ipPort = " + ipPort);
        return "ok";
    }
}
