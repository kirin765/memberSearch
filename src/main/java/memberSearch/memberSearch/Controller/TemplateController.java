package memberSearch.memberSearch.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {

    @GetMapping("/fragment")
    public String template() {
        return "thymeleaf/fragment/fragmentMain";
    }

    @GetMapping("/layout")
    public String layout() {
        return "thymeleaf/layout/layoutMain";
    }
//
//    @GetMapping("/layoutExtend")
//    public String layoutExtends() {
//        return "template/thymeleaf/layoutExtend/layoutExtendMain";
//    }
}
