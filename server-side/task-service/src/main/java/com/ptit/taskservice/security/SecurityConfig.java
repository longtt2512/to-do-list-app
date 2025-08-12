package com.ptit.taskservice.security;

import com.ptit.taskservice.entity.User;
import com.ptit.taskservice.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author long.truong
 */

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;

  public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
    this.jwtAuthFilter = jwtAuthFilter;
  }

  @Bean
  public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

  @Bean
  public UserDetailsService userDetailsService(UserRepository users) {
    return username -> users.findByUsername(username)
        .map(this::toSpringUser)
        .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
  }

  private org.springframework.security.core.userdetails.UserDetails toSpringUser(User u) {
    String[] authorities = u.getRoles().stream().map(r -> r.getName()).toArray(String[]::new);
    return org.springframework.security.core.userdetails.User.withUsername(u.getUsername())
        .password(u.getPassword())
        .authorities(authorities)
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(!u.isEnabled())
        .build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailsService uds, PasswordEncoder enc) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(uds);
    provider.setPasswordEncoder(enc);
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
    return cfg.getAuthenticationManager();
  }

  @Bean
  SecurityFilterChain api(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/login", "/auth/refresh",
                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                "/actuator/**").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  public static void main(String[] args) {
    // anywhere (e.g., a scratch @Test or CommandLineRunner)
    System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("password"));

  }
}
