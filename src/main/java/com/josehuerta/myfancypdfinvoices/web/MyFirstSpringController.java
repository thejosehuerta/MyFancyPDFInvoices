package com.josehuerta.myfancypdfinvoices.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/* This annotation is so @ComponentScan can find it and also let SpringMVC knows that this
class can accept HTTP requests */
@Controller
public class MyFirstSpringController {

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello world!";
    }
}
