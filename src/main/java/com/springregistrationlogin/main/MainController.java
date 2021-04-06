package com.springregistrationlogin.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class MainController {
    @GetMapping
    public String main() {
        return "index";
    }

}
