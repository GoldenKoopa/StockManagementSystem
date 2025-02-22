package com.sms.stockmanagementsystem.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {
    http.authorizeHttpRequests((authorize) -> {
          authorize.requestMatchers("/").permitAll();
          authorize.requestMatchers("/error").permitAll();
          authorize.anyRequest().authenticated();
        })
        .formLogin(Customizer.withDefaults())
        .logout(logout -> logout.permitAll())
        .csrf(csrf
              -> csrf.csrfTokenRepository(
                  CookieCsrfTokenRepository.withHttpOnlyFalse()));
    ;
    return http.build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
