package com.springproject.estates;

import com.springproject.estates.domain.Role;
import com.springproject.estates.domain.User;
import com.springproject.estates.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class EstatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstatesApplication.class, args);
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


/*    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            userService.saveRole(new Role(null,"ROLE_USER"));
            userService.saveRole(new Role(null,"ROLE_ADMIN"));

            userService.saveUser(new User(null, "jhon jhon", "jhon", "1234",new ArrayList<>()));
            userService.saveUser(new User(null, "marta marta", "marta", "1234",new ArrayList<>()));
            userService.saveUser(new User(null, "sali sali", "sali", "1234",new ArrayList<>()));

            userService.addRoleToUser("jhon", "ROLE_USER");
            userService.addRoleToUser("marta", "ROLE_USER");
            userService.addRoleToUser("sali", "ROLE_ADMIN");
        };
    }*/
}
