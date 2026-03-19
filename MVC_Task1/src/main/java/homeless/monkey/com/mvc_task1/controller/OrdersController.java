package homeless.monkey.com.mvc_task1.controller;

import com.fasterxml.jackson.annotation.JsonView;
import homeless.monkey.com.mvc_task1.dto.UserRequestDto;
import homeless.monkey.com.mvc_task1.jsonViews.JsonViews;
import homeless.monkey.com.mvc_task1.model.UserEntity;
import homeless.monkey.com.mvc_task1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class OrdersController {

    private final UserService userService;

    public OrdersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @JsonView(JsonViews.UserSummary.class)
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @JsonView(JsonViews.UserDetails.class)
    public ResponseEntity<?> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping()
    @JsonView(JsonViews.UserSummary.class)
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserRequestDto dto){
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
