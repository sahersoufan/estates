package com.springproject.estates.controllers;

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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;



    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            ));
        }catch (BadCredentialsException e){
            throw new Exception("Bad Credential >>> "+e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(
                authenticationRequest.getUsername()
        );
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }




    @RequestMapping(value = "/authenticateWithHtml", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestParam String username, @RequestParam String password, HttpServletResponse httpServletResponse)throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username,
                    password
            ));
        }catch (BadCredentialsException e){
            throw new Exception("Bad Credential >>> "+e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(
                username
        );
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwt);
        Cookie cookie = new Cookie("jwt", authenticationResponse.getJwt());
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        httpServletResponse.addHeader("Access-Control-Allow-Credentials","true");
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok().body(httpServletResponse);
    }}
