package homeless.monkey.com.oauth2.config;

import homeless.monkey.com.oauth2.service.SocialAppService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.net.HttpURLConnection;
import java.net.URL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SocialAppService socialAppService;
    private final OAuth2AuthorizedClientService clientService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/logout"))
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/error").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, accessDeniedException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            logger.warn("Access denied for user: {}",
                                    request.getUserPrincipal() != null
                                            ? request.getUserPrincipal().getName()
                                            : "anonymous");
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                        })
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/oauth2/authorization/google")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(socialAppService))
                        .defaultSuccessUrl("/user")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(oidcLogoutSuccessHandler())
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    public LogoutSuccessHandler oidcLogoutSuccessHandler() {
        return (request, response, authentication) -> {

            if (authentication instanceof OAuth2AuthenticationToken oauthToken) {

                OAuth2AuthorizedClient client =
                        clientService.loadAuthorizedClient(
                                oauthToken.getAuthorizedClientRegistrationId(),
                                oauthToken.getName()
                        );

                if (client != null) {
                    String accessToken = client.getAccessToken().getTokenValue();

                    try {
                        URL url = new URL("https://oauth2.googleapis.com/revoke?token=" + accessToken);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.getResponseCode();

                        logger.info("Access token revoked for user: {}", oauthToken.getName());

                    } catch (Exception e) {
                        logger.error("Error revoking token", e);
                    }
                }
            }

            if (authentication != null && authentication.getPrincipal() instanceof OAuth2User principal) {
                String email = principal.getAttribute("email");
                logger.info("User logout: {}", email);
            }

            response.sendRedirect("/");
        };
    }
}