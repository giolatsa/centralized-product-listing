package com.epam.centralized.product.listing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            (authorize) ->
                authorize
                    .requestMatchers("style.css", "/register/**", "/login", "static/**", "icons/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin((form) -> form.loginPage("/login").permitAll())
        .logout(
            (logout) ->
                logout
                    .logoutUrl(
                        "/logout") // Specify the logout URL if different from the default "/logout"
                    .logoutSuccessUrl("/login?logout") // Specify a URL to redirect to after logout
                    .invalidateHttpSession(true) // Invalidate session after logout
                    .deleteCookies("JSESSIONID") // Delete session cookie
                    .permitAll());

    return http.build();
  }
}
