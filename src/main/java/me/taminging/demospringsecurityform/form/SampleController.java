package me.taminging.demospringsecurityform.form;

import me.taminging.demospringsecurityform.account.AccountContext;
import me.taminging.demospringsecurityform.account.AccountRepository;
import me.taminging.demospringsecurityform.common.SecurityLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@Controller
public class SampleController {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/")
    public String index(Model model, Principal principal){
        if(principal == null)
            model.addAttribute("messages", "Hello Spring Security");
        else
            model.addAttribute("messages", "Hello,"  + principal.getName());

        return "index";
    }
    @GetMapping("/info")
    public String info(Model model){
        model.addAttribute("messages", "info");
        return "info";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("messages", "Hello " + principal.getName());
        AccountContext.setAccount(accountRepository.findByUsername(principal.getName()));
        sampleService.dashboard();
        return "dashboard";
    }
    @GetMapping("/admin")
    public String admin(Model model, Principal principal){
        model.addAttribute("messages", "Hello Admin, " + principal.getName());
        return "admin";
    }
    @GetMapping("/user")
    public String user(Model model, Principal principal){
        model.addAttribute("messages", "Hello User, " + principal.getName());
        return "user";
    }

    //WebAsyncManagerIntegrationFilter : 서로 다른 스레드여도 같은 Principal이 나올 수 있도록 도와주는 필터
    //즉 스레드적으로 갈려도 같은 인증으로 처리 될 수 있다
    @GetMapping("async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {
        SecurityLogger.log("MVC");
        return () -> {
            SecurityLogger.log("Callable");
            return "Async Handler";
        };
    }
}
