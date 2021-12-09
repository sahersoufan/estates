package com.springproject.estates;

import com.springproject.estates.domain.Role;
import com.springproject.estates.domain.User;
import com.springproject.estates.services.UserService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
@EnableRabbit
@EnableCaching
@SpringBootApplication
public class EstatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstatesApplication.class, args);
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
