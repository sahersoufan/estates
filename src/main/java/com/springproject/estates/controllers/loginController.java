package com.springproject.estates.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class loginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
