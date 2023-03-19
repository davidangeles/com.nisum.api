package com.nisum.api.config;

import com.nisum.api.security.JwtAuthenticationFilter;
import com.nisum.api.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    public static final String[] requestPermit = {
            "/h2-console/**",
            "/actuator/**",
            "/token",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs"
    };
    private final TokenService tokenService;
    private final Environment environment;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getOutputStream().println("{ \"mensaje\": \"" + "No autorizado" + "\" }");
                        }
                )
                .and();

        http.authorizeRequests()
                .antMatchers(requestPermit).permitAll()
                .anyRequest().authenticated()
                .and();

        http.headers().frameOptions().disable()
                .and();

        http.addFilterBefore(new JwtAuthenticationFilter(environment, tokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
