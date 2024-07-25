package com.example.kafkaoauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

/**
 * Configures the security settings for the application, including:
 * - Disabling CSRF protection
 * - Requiring all requests to be authenticated
 * - Disabling anonymous access
 * - Enabling OAuth2 login and client support
 * - Enabling JWT-based resource server configuration
 * - Enabling Vaadin security configuration
 *
 * @param httpSecurity The HttpSecurity object to configure
 * @return The configured SecurityFilterChain
 * @throws Exception If there is an error configuring the security settings
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigs extends VaadinWebSecurity {

    // ... existing code ...

    SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.authorizeHttpRequests( req -> {
            req.anyRequest().authenticated();
        });

        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.anonymous(AbstractHttpConfigurer::disable);
        httpSecurity.oauth2Login(Customizer.withDefaults());
        httpSecurity.oauth2Client(Customizer.withDefaults());
        httpSecurity.oauth2ResourceServer(resourceServerConfigurer -> {
            resourceServerConfigurer.jwt(Customizer.withDefaults());
        });
        super.configure(httpSecurity);
        return httpSecurity.build();
    }
}
