package memberSearch.memberSearch.Controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.IpPort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ConverterController {

    @GetMapping("/converter-view")
    public String converterView(Model model){

        model.addAttribute("number", Integer.valueOf(123));
        model.addAttribute("ipPort", new IpPort("127.0.0.1", "8080"));

        return "converter-view";
    }

    @GetMapping("/converter-form")
    public String coverterForm(Model model){
        model.addAttribute("form", new Form(new IpPort("127.0.0.1", "8080")));
        return "converter-form";
    }

    @Data
    public class Form{
        private IpPort ipPort;

        public Form(IpPort ipPort) {
            this.ipPort = ipPort;
        }
    }
}
