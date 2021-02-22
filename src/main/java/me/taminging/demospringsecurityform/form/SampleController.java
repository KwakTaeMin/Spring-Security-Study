package me.taminging.demospringsecurityform.form;

import me.taminging.demospringsecurityform.account.AccountContext;
import me.taminging.demospringsecurityform.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

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
}
