package homeless.monkey.com.security_task2.security;

import homeless.monkey.com.security_task2.security.authenticationHandler.OurAuthenticationFailureHandler;
import homeless.monkey.com.security_task2.security.authenticationHandler.OurAuthenticationSuccessHandler;
import homeless.monkey.com.security_task2.security.filter.JwtAuthenticationFilter;
import homeless.monkey.com.security_task2.security.filter.LoggingFilter;
import homeless.monkey.com.security_task2.security.filter.OurUsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OurAuthenticationFailureHandler failureHandler;
    private final OurAuthenticationSuccessHandler successHandler;
    private final OurUserDetailedService ourUserDetailedService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final LoggingFilter loggingFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        OurUsernamePasswordAuthenticationFilter loginFilter = new OurUsernamePasswordAuthenticationFilter();
        loginFilter.setAuthenticationManager(authenticationManager(httpSecurity.getSharedObject(AuthenticationConfiguration.class)));
        loginFilter.setAuthenticationSuccessHandler(successHandler);
        loginFilter.setAuthenticationFailureHandler(failureHandler);

        httpSecurity.csrf(configurer -> configurer.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/login",
                                         "/api/v1/admin/refresh",
                                         "/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(ourUserDetailedService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
