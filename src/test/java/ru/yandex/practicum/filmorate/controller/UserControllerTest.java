package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUserValidName() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        user.setBirthday(LocalDate.of(1990, 1, 1));


        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateUserWithInvalidLogin() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin(""); // Пустой логин
        user.setBirthday(LocalDate.of(1990, 1, 1));

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.login").value("must not be blank"));
    }

    @Test
    public void testCreateUserWithInvalidEmail() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doeexample.com"); // не валидный
        user.setLogin("qwerty");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("must be a well-formed email address"));
    }

    @Test
    public void testCreateUserWithInvalidData() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.@doeexample.com");
        user.setLogin("qwerty");
        user.setBirthday(LocalDate.of(2990, 1, 1)); // не валидный

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUserWithValidData() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.@doeexample.com");
        user.setLogin("qwerty");
        user.setBirthday(LocalDate.now()); // валидный

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

}

