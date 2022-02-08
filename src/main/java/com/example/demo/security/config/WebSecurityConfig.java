package com.example.demo.security.config;

import com.example.demo.appDoctor.AppDoctorService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
//enable Spring Security’s web security support and provide the Spring MVC integration


public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppDoctorService appDoctorService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //I can send post requests without being rejected
                //crsf  = Cross-Site Request Forgery attack
                .authorizeRequests()
                    .antMatchers("/api/v*/hospital/**")
                // this HttpSecurity will only be applicable to URLs that start with the path
                    .permitAll();
                //i want to allow everything under registration
               // .anyRequest()
               // .authenticated().and()
                //.formLogin();
        //we want to require all users to be authenticated

    }


    //defines which URL paths should be secured and which should not.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    //retrieves the user details from a simple, read-only user DAO – the UserDetailsService.
    // User Details Service only has access to the username in order to retrieve
    // the full user entity.
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        authProvider.setUserDetailsService(appDoctorService);
        return authProvider;
    }
}