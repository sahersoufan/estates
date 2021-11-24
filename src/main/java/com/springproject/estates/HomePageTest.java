package com.springproject.estates;

import com.springproject.estates.models.AuthenticationRequest;
import com.springproject.estates.models.AuthenticationResponse;
import com.springproject.estates.services.MyUserDetailsService;
import com.springproject.estates.services.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomePageTest {

    @RequestMapping("/user")
    public String user(){
        return "hello World";
    }
    @RequestMapping("/admin")
    public String admin(){
        return "hello World";
    }


}
