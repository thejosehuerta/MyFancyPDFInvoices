package com.josehuerta.myfancypdfinvoices.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
