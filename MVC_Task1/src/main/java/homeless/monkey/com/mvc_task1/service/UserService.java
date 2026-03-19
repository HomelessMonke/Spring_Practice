package homeless.monkey.com.mvc_task1.service;

import homeless.monkey.com.mvc_task1.dto.UserRequestDto;
import homeless.monkey.com.mvc_task1.exception.EmailAlreadyExistException;
import homeless.monkey.com.mvc_task1.exception.UserNotFoundException;
import homeless.monkey.com.mvc_task1.model.UserEntity;
import homeless.monkey.com.mvc_task1.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserEntity createUser(UserRequestDto dto){
        var email = dto.email();
        if (userRepository.existsByEmail(email))
            throw new EmailAlreadyExistException(email);

        UserEntity user = new UserEntity();
        user.setName(dto.name());
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));
    }

    public void deleteUser(Long id) {
        userRepository.delete(getUser(id));
    }

    public UserEntity updateUser(Long id, UserRequestDto dto) {
        UserEntity user = getUser(id);
        user.setName(dto.name());
        user.setEmail(dto.email());
        userRepository.save(user);

        return user;
    }
}
