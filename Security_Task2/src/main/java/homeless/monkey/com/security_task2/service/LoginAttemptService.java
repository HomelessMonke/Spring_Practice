package homeless.monkey.com.security_task2.service;

import homeless.monkey.com.security_task2.entity.UserEntity;
import homeless.monkey.com.security_task2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginAttemptService {

    private static final int MAX_FAILED_ATTEMPTS = 5;

    private final UserRepository userRepository;

    public void recordFailedAttempt(String username) {

        UserEntity user = getUserByName(username);
        if (!user.getIsAccountNonLocked())
            return;

        user.setFailureAttempts(user.getFailureAttempts() + 1);
        if (user.getFailureAttempts() >= MAX_FAILED_ATTEMPTS)
            user.setIsAccountNonLocked(false);

        userRepository.save(user);
    }

    public void resetFailedAttempts(String username) {
        UserEntity user = getUserByName(username);
        resetFailedAttempts(user);
    }

    public void unlockAccount(Long id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id:" + id + " not found"));

        resetFailedAttempts(user);
    }

    private void resetFailedAttempts(UserEntity user){
        user.setFailureAttempts(0);
        user.setIsAccountNonLocked(true);
        userRepository.save(user);
    }

    private UserEntity getUserByName(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username:" + username + " not found"));
    }
}
