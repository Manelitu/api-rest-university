package com.api.restuniversity.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    final private SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/users").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/courses", "/api/v1/discipline").hasAuthority("ROLE_COORDINATOR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/courses/**", "/api/v1/discipline/**").hasAuthority("ROLE_COORDINATOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/courses", "/api/v1/discipline").hasAuthority("ROLE_COORDINATOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/courses", "/api/v1/discipline").hasAuthority("ROLE_COORDINATOR")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
