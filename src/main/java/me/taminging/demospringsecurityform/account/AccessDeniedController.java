package me.taminging.demospringsecurityform.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AccessDeniedController {


    @GetMapping("access-denied")
    public String accessDenied(Principal principal, Model model){

        model.addAttribute("name", principal.getName());
        return "access-denied";

    }
}
