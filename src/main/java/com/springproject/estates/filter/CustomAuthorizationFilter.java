package com.springproject.estates.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springproject.estates.security.ExpiredToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {



    ExpiredToken expiredToken=new ExpiredToken();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals("/login") ||
                request.getServletPath().equals("/api/checkExpire") ||
                request.getServletPath().equals("/loginPublic") ||
                request.getServletPath().equals("/") ||
                request.getServletPath().startsWith("/webjars") ||
                request.getServletPath().startsWith("/js") ||
                request.getServletPath().startsWith("/css") ||
                request.getServletPath().startsWith("/lib") ||
                request.getServletPath().startsWith("/plugins") ||
                request.getServletPath().startsWith("/styles") ||
                request.getServletPath().equals("/user") ||
                request.getServletPath().equals("/admin/register") ||
                request.getServletPath().equals("/admin/addroletouser") ||
                request.getServletPath().equals("/admin")

        ){
                filterChain.doFilter(request,response);
        }else{

            try {
                int value= expiredToken.checkExpire(request);
                if(value == 0){
                    RequestDispatcher requestDispatcher = request
                            .getRequestDispatcher(request.getServletPath());
                    requestDispatcher.forward(request, response);

                   // filterChain.doFilter(request,response);
                }
                else if(value == 1){
                String url=request.getServletPath();
                request.setAttribute("url",url);
                    RequestDispatcher requestDispatcher = request
                            .getRequestDispatcher("/api/token/refresh");
                    requestDispatcher.forward(request, response);


                }else {
                    RequestDispatcher requestDispatcher = request
                            .getRequestDispatcher("/loginPublic");
                    requestDispatcher.forward(request, response);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
