package br.com.fiap.SpringSecurityInitial.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.com.fiap.SpringSecurityInitial.services.UserStubbedDetailService;
import br.com.fiap.SpringSecurityInitial.support.encoder.PlainTextPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserStubbedDetailService _userService;

    public SecurityConfig(final UserStubbedDetailService userService) {
        this._userService = userService;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new PlainTextPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authetnicationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(_userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequest -> {
            authorizeRequest.requestMatchers("/publica").permitAll();
            authorizeRequest.requestMatchers("/logout").permitAll();
            authorizeRequest.anyRequest().authenticated();
        });
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }
}
