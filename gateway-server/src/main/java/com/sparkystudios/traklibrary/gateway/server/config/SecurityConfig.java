package com.sparkystudios.traklibrary.gateway.server.config;

import com.sparkystudios.traklibrary.security.configuration.JwtConfig;
import com.sparkystudios.traklibrary.security.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(@Value("${trak.security.allowed-origins}") String[] allowedOrigins) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(allowedOrigins));
        corsConfiguration.setAllowedMethods(List.of("GET", "PUT", "POST", "DELETE", "PATCH"));
        corsConfiguration.setAllowedHeaders(List.of("Origin", "Content-Type", "Accept", "Authorization"));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }

    @Order(1)
    @Configuration
    @RequiredArgsConstructor
    public static class JwtSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final JwtConfig jwtConfig;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .requestMatchers()
                    .antMatchers("/auth/**", "/games/**", "/images/**", "/notifications/**")
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/auth", "/auth/users").permitAll()
                    .antMatchers(HttpMethod.PUT, "/auth/users", "/auth/users/recover").permitAll()
                    .antMatchers(HttpMethod.GET, "/images/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/games/**/image").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .cors()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint((req, res, e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                    .and()
                    .csrf().disable()
                    .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtConfig));
        }
    }

    @Order(2)
    @Configuration
    @RequiredArgsConstructor
    public static class BasicSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Value("${trak.security.user.email.username}")
        private String username;

        @Value("${trak.security.user.email.password}")
        private String password;

        private final MyBasicAuthenticationEntryPoint authenticationEntryPoint;
        private final PasswordEncoder passwordEncoder;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/emails/**")
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .cors()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                    .csrf().disable();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser(username)
                    .password(passwordEncoder.encode(password))
                    .authorities("ROLE_EMAIL_USER");
        }
    }
}