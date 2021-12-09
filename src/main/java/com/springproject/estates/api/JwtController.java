package com.springproject.estates.api;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.springproject.estates.domain.Role;
import com.springproject.estates.domain.User;
import com.springproject.estates.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class JwtController {

    private final UserService userService;


    @GetMapping("/token/refresh")
    public String refreshToke(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = (String) request.getAttribute("url");

        Cookie[] authCookie = request.getCookies();
        final String[] access_token_cookie = new String[1];
        stream(authCookie).forEach(cookie -> {
            if (cookie.getName().equals("refresh_token")) {
                access_token_cookie[0] = cookie.getValue();
            }
        });
        if (access_token_cookie[0] != null) {
            try {
                String refresh_token = access_token_cookie[0];
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                Cookie myCookie1 = new Cookie("access_token", access_token);
                Cookie myCookie2 = new Cookie("refresh_token", refresh_token);
                myCookie1.setPath("/");
                myCookie2.setPath("/");
                response.addCookie(myCookie1);
                response.addCookie(myCookie2);

                return "redirect:" + url;
            } catch (Exception e) {
                /*return "redirect:/loginPublic";*/
                throw e;
            }
        } else {
            return "redirect:/loginPublic";
        }
    }
}