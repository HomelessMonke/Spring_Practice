package homeless.monkey.com.security_task2.security.authenticationHandler;

import homeless.monkey.com.security_task2.service.LoginAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OurAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final LoginAttemptService loginAttemptService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        Authentication auth = exception.getAuthenticationRequest();
        if (auth != null){
            String username = auth.getName();
            if (username != null) {
                loginAttemptService.recordFailedAttempt(username);
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String message = "Invalid credentials";

        if (exception instanceof LockedException) {
            message = "Your account has been locked due to too many failed login attempts. Please try again later.";
        } else if (exception instanceof BadCredentialsException) {
            message = "Invalid username or password.";
        }

        Map<String, String> error = Map.of("error", message);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
