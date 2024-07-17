package com.example.kafkaoauth;

import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {

    SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests( req -> {
            req.requestMatchers("/produce").authenticated();
            req.anyRequest().permitAll();
        });
        httpSecurity.anonymous(AbstractHttpConfigurer::disable);
        httpSecurity.oauth2Login(Customizer.withDefaults());
        httpSecurity.oauth2Client(Customizer.withDefaults());
        httpSecurity.oauth2ResourceServer(resourceServerConfigurer -> {
            resourceServerConfigurer.jwt(Customizer.withDefaults());
        });
        return httpSecurity.build();
    }


}
