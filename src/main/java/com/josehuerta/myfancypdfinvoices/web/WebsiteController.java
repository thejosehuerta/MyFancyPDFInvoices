package com.josehuerta.myfancypdfinvoices.web;

import com.josehuerta.myfancypdfinvoices.web.forms.LoginForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class WebsiteController {

    @GetMapping("/")
    /*
    Here, the model parameter is essentially a map containing all the variables we want to use in our Thymeleaf
    templates.
    */
    public String homepage(Model model, @RequestParam(name = "username", required = false, defaultValue = "stranger") String username) {
        model.addAttribute("username", username);
        model.addAttribute("currentDate", LocalDateTime.now());
        return "index.html";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login.html";
    }

    @PostMapping("/login")
    /*
    The @ModelAttribute annotation makes sure that you can access the loginForm in the model
    without having to manually add it.
    */
    public String login(@ModelAttribute @Valid LoginForm loginForm, BindingResult bindingResult, Model model) {
        /*
        The BindingResult parameter is basically a container for our validation errors.
        */
        if(bindingResult.hasErrors()) {
            return "login.html";
        }
        if(loginForm.getUsername().equals(loginForm.getPassword())) {
            return "redirect:/";
        }
        model.addAttribute("invalidCredentials", "true");
        return "login.html";
    }
}
