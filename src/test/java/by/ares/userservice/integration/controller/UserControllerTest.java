package by.ares.userservice.integration.controller;

import by.ares.userservice.dto.request.ActivationStatusRequest;
import by.ares.userservice.dto.request.UserRequest;
import by.ares.userservice.integration.controller.abstraction.AbstractIntegrationTest;
import by.ares.userservice.model.User;
import by.ares.userservice.repository.UserRepository;
import by.ares.userservice.service.SecurityValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static by.ares.userservice.util.TestConstants.*;
import static by.ares.userservice.util.TestModelBuilder.buildUser;
import static by.ares.userservice.util.TestModelBuilder.buildUserRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends AbstractIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @MockitoBean
    private SecurityValidationService securityValidationService;

    private User user;


    @BeforeEach
    void init() {
        user = saveTestUser();
    }

    private User saveTestUser() {
        return userRepository.save(buildUser());
    }


    @Test
    void shouldFindUserById() throws Exception {
        mockMvc.perform(get("/users/{id}", user.getId())
                        .header("X-User-Id", user.getId())
                        .header("X-User-Role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(USER_NAME));
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserRequest request = buildUserRequest(USER_EMAIL_2);
        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-User-Id", user.getId())
                        .header("X-User-Role", "ADMIN"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Long userId = Long.valueOf(response);
        mockMvc.perform(get("/users/{id}", userId)
                        .header("X-User-Id", user.getId())
                        .header("X-User-Role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserRequest request = buildUserRequest(USER_EMAIL_3);
        request.setSurname(UPDATED_USER_SURNAME);
        mockMvc.perform(put("/users/{id}", user.getId())
                        .header("X-User-Id", user.getId())
                        .header("X-User-Role", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(content().string(String.valueOf(user.getId())));
    }

    @Test
    void shouldChangeUserStatus() throws Exception {
        mockMvc.perform(patch("/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ActivationStatusRequest(INACTIVE)))
                        .header("X-User-Id", user.getId())
                        .header("X-User-Role", "ADMIN"))
                .andExpect(status().isAccepted())
                .andExpect(content().string(String.valueOf(user.getId())));
        mockMvc.perform(get("/users/{id}", user.getId())
                        .header("X-User-Id", user.getId())
                        .header("X-User-Role", "ADMIN"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", user.getId())
                .header("X-User-Role", "ADMIN"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnPage() throws Exception {
        final User user2 = saveTestUser();
        final User user3 = saveTestUser();
        user2.setEmail(USER_EMAIL_4);
        user3.setEmail(USER_EMAIL_5);
        mockMvc.perform(get("/users?page=0&size=2&name=Nick"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

}
