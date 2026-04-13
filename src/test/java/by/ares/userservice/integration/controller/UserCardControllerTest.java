package by.ares.userservice.integration.controller;

import by.ares.userservice.integration.controller.abstraction.AbstractIntegrationTest;
import by.ares.userservice.model.PaymentCard;
import by.ares.userservice.model.User;
import by.ares.userservice.repository.PaymentCardRepository;
import by.ares.userservice.repository.UserRepository;
import by.ares.userservice.service.SecurityValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static by.ares.userservice.util.TestModelBuilder.buildPaymentCard;
import static by.ares.userservice.util.TestModelBuilder.buildUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserCardControllerTest extends AbstractIntegrationTest {

    @MockitoBean
    private SecurityValidationService securityValidationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PaymentCardRepository paymentCardRepository;

    private User user;

    @BeforeEach
    void init() {
        user = saveTestUser();
        savePaymentCard(user);
    }

    private User saveTestUser() {
        return userRepository.save(buildUser());
    }

    private PaymentCard savePaymentCard(User user) {
        return paymentCardRepository.save(buildPaymentCard(user));
    }

    @Test
    void shouldFindAllUserCards() throws Exception {
        mockMvc.perform(get("/users/{userId}/payment-cards", user.getId())
                .header("X-User-Id", user.getId())
                .header("X-User-Role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

}