package com.example.budgetbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("!local")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Allow public access to static resources
                .requestMatchers("/css/**", "/js/**", "/images/**", "/vue/**").permitAll()
                // Allow H2 console access (for development only)
                .requestMatchers("/h2-console/**").permitAll()
                // Require authentication for all other requests
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                // Custom login page (optional)
                .loginPage("/oauth2/authorization/azure")
                .defaultSuccessUrl("/snappies", true)
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        // Allow H2 console to be embedded in frames (for development only)
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        
        // Disable CSRF for H2 console (for development only)
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        return http.build();
    }
}
