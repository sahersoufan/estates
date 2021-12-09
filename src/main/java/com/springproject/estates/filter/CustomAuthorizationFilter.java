package com.springproject.estates.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
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
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals("/login") ||
                request.getServletPath().equals("/loginPublic") ||
                request.getServletPath().equals("/") ||
                request.getServletPath().startsWith("/webjars") ||
                request.getServletPath().startsWith("/js") ||
                request.getServletPath().startsWith("/css") ||
                request.getServletPath().startsWith("/lib") ||
                request.getServletPath().startsWith("/plugins") ||
                request.getServletPath().startsWith("/styles") ||
                request.getServletPath().equals("/user")


        ) {
            filterChain.doFilter(request, response);
        } else {
            Cookie[] authCookie = request.getCookies();
            final String[] token_cookie = new String[2];
            stream(authCookie).forEach(cookie -> {
                if (cookie.getName().equals("access_token")){
                    token_cookie[0] = cookie.getValue();
                }
                if (cookie.getName().equals("refresh_token")){
                    token_cookie[1] = cookie.getValue();
                }
            });
            if (token_cookie[0] != null) {
                try {
                    String token = token_cookie[0];
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    try {
                        String url=request.getServletPath();
                        request.setAttribute("url",url);
                        RequestDispatcher requestDispatcher = request
                                .getRequestDispatcher("/api/token/refresh");
                        requestDispatcher.forward(request, response);

                    }catch (Exception e1){
                        if (e1.getCause().toString().contains("Expired")){
                            log.error("Error logging in: {}", e.getMessage());
                            RequestDispatcher requestDispatcher = request
                                    .getRequestDispatcher("/loginPublic");
                            requestDispatcher.forward(request, response);

                        }else {
                            log.error("Error logging in: {}", e.getMessage());
                            response.setHeader("error", e.getMessage());
                            response.setStatus(FORBIDDEN.value());
                            Map<String, String> error = new HashMap<>();
                            error.put("error_message", e.getMessage());
                            response.setContentType(APPLICATION_JSON_VALUE);
                            new ObjectMapper().writeValue(response.getOutputStream(), error);
                        }
                    }
                }
            } else {
                filterChain.doFilter(request, response);
            }

        }
    }
}
