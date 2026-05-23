package br.com.fiap.SpringSecurityInitial.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import br.com.fiap.SpringSecurityInitial.support.encoder.PlainTextPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) {
        final AuthenticationManagerBuilder builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetails()).passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    UserDetailsService userDetails() {
        final InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("usuario")
                        .password(passwordEncoder().encode("senha"))
                        .roles("USER")
                        .build());
        manager.createUser(
                User.withUsername("admin")
                        .password(passwordEncoder().encode("password"))
                        .roles("ADMIN", "USER")
                        .build());
        return manager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new PlainTextPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequest -> {
            authorizeRequest.requestMatchers("/publica").permitAll();
            authorizeRequest.requestMatchers("/logout").permitAll();
            authorizeRequest.anyRequest().authenticated();
        });
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
