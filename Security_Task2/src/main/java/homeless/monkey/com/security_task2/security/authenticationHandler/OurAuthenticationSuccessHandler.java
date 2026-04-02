package homeless.monkey.com.security_task2.security.authenticationHandler;

import homeless.monkey.com.security_task2.dto.LoginResponseDto;
import homeless.monkey.com.security_task2.service.LoginAttemptService;
import homeless.monkey.com.security_task2.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OurAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtils jwtUtils;
    private final LoginAttemptService loginAttemptService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        loginAttemptService.resetFailedAttempts(username);

        String accessToken = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);

        LoginResponseDto responseDto = new LoginResponseDto(accessToken, refreshToken);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));
    }
}
