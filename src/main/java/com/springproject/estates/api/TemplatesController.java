package com.springproject.estates.api;

import com.springproject.estates.domain.User;
import com.springproject.estates.services.UserService;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TemplatesController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/loginPublic")
    public String loginPage(){
        return "loginPublic.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Cookie myCookie1 =  new Cookie("access_token", null);
        Cookie myCookie2 =  new Cookie("refresh_token", null);
        myCookie1.setPath("/");
        myCookie2.setPath("/");
        response.addCookie(myCookie1);
        response.addCookie(myCookie2);
        return "redirect:/loginPublic";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String register() {
        return "register";
    }
    @RequestMapping(method = RequestMethod.POST, value = "/save/user")
    public String saveuser(@ModelAttribute User user) {

        userService.saveUser(user);
        return "redirect:/loginPublic";
    }



}
