package com.springproject.estates.api;

import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthController {

    @RequestMapping(method = RequestMethod.GET, value = "/loginPage")
    public String loginPage(){
        return "loginPage.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUsers")
        public String getUsers(){
            return "getUsers.html";
    }

}
