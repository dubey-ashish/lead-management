package com.udaan.leads.security.config;

import com.udaan.leads.security.encoder.Base64PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) //enables @Secured & [@RolesAllowed and @PermitAll]
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService; //bean of whichever class implements UserDetailsService. In this case security/service/MongoAuthUserDetailService

    //Responsible for processing AUTHENTICATION requests
    //Creates an authentication object once the credentials are verified
    @Bean
    public AuthenticationManager customAuthenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)  //public class MongoAuthUserDetailService implements UserDetailsService. security/service/MongoAuthUserDetailService
                .passwordEncoder(passwordEncoder()); //Spring Security uses the password encoder to encode the entered password (from the login request) and then compares the encoded entered password with the stored encoded password to check if they match.
        return authenticationManagerBuilder.build();
    }

    @Bean
    public Base64PasswordEncoder passwordEncoder() {
        return new Base64PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.anyRequest().permitAll())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
