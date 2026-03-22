package homeless.monkey.com.mvc_task1.controller;

import homeless.monkey.com.mvc_task1.model.UserEntity;
import homeless.monkey.com.mvc_task1.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnUsersWithoutOrders() throws Exception {
        UserEntity user = new UserEntity(1L, "Tolya", "test@itk.com", List.of());
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].email").exists())
                .andExpect(jsonPath("$[0].orders").doesNotExist());
    }

    @Test
    void shouldReturnUserWithOrders() throws Exception {
        UserEntity user = new UserEntity(1L, "Tolya", "test@itk.com", List.of());
        Mockito.when(userService.getUser(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.orders").exists());
    }
}