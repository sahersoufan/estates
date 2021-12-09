package com.springproject.estates.security;

import com.springproject.estates.filter.CustomAuthenticationFilter;
import com.springproject.estates.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration @EnableWebSecurity @RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers( HttpMethod.POST,
                "/login/**",
                "/save/user",
                "/api/user/save"
                ).permitAll();

        http.authorizeRequests().antMatchers(HttpMethod.GET,
                "/",
                "/loginPublic/**",
                "/api/token/refresh/**",
                "/admin",
                "/register",
                "/estate/**",
                "/add/estate",
                "/css/**",
                "/lib/**",
                "/styles/**",
                "/plugins/**",
                "/img/**",
                "/lib/**",
                "/scss/**",
                "/user",
                "/webjars/**",
                "/js/**").permitAll();

        http.authorizeRequests().antMatchers( HttpMethod.GET,
                "/api/user/**",
                "/estate"
        ).hasAnyAuthority("ROLE_USER");

        http.authorizeRequests().antMatchers(
                "/api/user/save/**",
                "/api/role/addtouser",
                 "/admin/home/content",
                 "/admin/addroletouser/content",
                "/admin/register/content"
        ).hasAnyAuthority("ROLE_ADMIN");

        http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/loginPublic").permitAll();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);





    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
