package com.example.OLS.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    final private UserDetailsService userDetailsService;

    private final CustomSuccessHandler customSuccessHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          return http.csrf(customizer -> customizer.disable())
                  .authorizeHttpRequests(requests -> requests
                          /// Allow registration without authentication
                          .requestMatchers(HttpMethod.GET, "/OLS/register").permitAll()
                          .requestMatchers(HttpMethod.POST, "/OLS/register", "/OLS/saveUser").permitAll()
                          /// Books : users and Admin can view
                          .requestMatchers(HttpMethod.GET,"/book/**").hasAnyRole("USER","ADMIN")
                          /// BOOKS: only ADMIN can add/update/delete
                          .requestMatchers(HttpMethod.POST, "/book/**").hasRole("ADMIN")
                          .requestMatchers(HttpMethod.PUT, "/book/**").hasRole("ADMIN")
                          .requestMatchers(HttpMethod.DELETE, "/book/**").hasRole("ADMIN")
                          /// Only Admin can manage users
                          .requestMatchers("/admin/**").hasRole("ADMIN")
                          .requestMatchers("/home").hasAnyRole("USER", "ADMIN")
                          .requestMatchers("/api/user/**").hasRole("ADMIN")
                          .requestMatchers(HttpMethod.GET,"/issue/getalltransactions").hasRole("ADMIN")
                          .requestMatchers(HttpMethod.POST,"/issue/**").hasAnyRole("ADMIN","USER")
                          .requestMatchers(HttpMethod.DELETE,"/issue/**").hasRole("ADMIN")
                          .anyRequest().authenticated())
                  .formLogin(form -> form
                          .loginPage("/OLS/login")
                          .successHandler(customSuccessHandler)  // redirect based on role
                          .permitAll()
                  )
                  .logout(logout -> logout
                          .logoutUrl("/logout")
                          .logoutSuccessUrl("/login?logout=true")
                          .permitAll()
                  )
                  .httpBasic(Customizer.withDefaults())
                  .build();


    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
