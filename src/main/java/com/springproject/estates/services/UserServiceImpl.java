package com.springproject.estates.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springproject.estates.domain.Role;
import com.springproject.estates.domain.User;
import com.springproject.estates.repository.RoleRepo;
import com.springproject.estates.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null){
            log.error("usern not found in the database");
            throw new  UsernameNotFoundException("user not found in the database");
        }else{
            log.info("user found in the database: {}",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new Role {} to the database", role.getName());

        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String RoleName) {
        log.info("adding Role {} to user {}", RoleName, username);

        User user = userRepo.findByUsername((username));
        Role role = roleRepo.findByName(RoleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("fetching user {}", username);

        return userRepo.findByUsername(username);
    }

    @Override
    public User getUser(HttpServletRequest request) {
        String url= (String) request.getAttribute("url");

        Cookie[] authCookie = request.getCookies();
        final String[] access_token_cookie = new String[1];
        stream(authCookie).forEach(cookie -> {
            if (cookie.getName().equals("access_token")){
                access_token_cookie[0] = cookie.getValue();
            }
        });
        if(access_token_cookie[0]!=null){
            try {
                String refresh_token = access_token_cookie[0];
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();


                return userRepo.findByUsername(username);
            }catch (Exception e){
                return null ;
            }
        }else{
            return null;
        }

    }

    @Override
    public List<User> getUsers() {
        log.info("fetching all users");
        return userRepo.findAll();
    }
}
