package homeless.monkey.com.security_task2.controller;

import homeless.monkey.com.security_task2.dto.LoginResponseDto;
import homeless.monkey.com.security_task2.service.LoginAttemptService;
import homeless.monkey.com.security_task2.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final LoginAttemptService loginService;
    private final UserDetailsService userDetailsService;

    private final JwtUtils jwtUtils;

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> getUser(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/users")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Void> createUser(){
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> unlockAccount(@PathVariable Long id){
        loginService.unlockAccount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        String username = jwtUtils.extractUsername(refreshToken);
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (jwtUtils.isTokenValid(refreshToken, user)) {
            return ResponseEntity.ok(new LoginResponseDto(
                    jwtUtils.generateToken(user),
                    jwtUtils.generateRefreshToken(user)
            ));
        }
        return ResponseEntity.status(401).body("Invalid refresh token");
    }
}
