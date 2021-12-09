package com.springproject.estates.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/admin")
public class AdminController {


    @RequestMapping(method = RequestMethod.GET)
    public String HomePage(){
        return "admin/home.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String RegisterPage(){
        return "admin/registration.html";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/addroletouser")
    public String addRoleToUserPage(){
        return "admin/addRoleToUser.html";
    }



}
