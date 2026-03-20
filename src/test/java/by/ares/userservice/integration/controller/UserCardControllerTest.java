package by.ares.userservice.integration.controller;

import by.ares.userservice.integration.controller.abstraction.AbstractIntegrationTest;
import by.ares.userservice.model.PaymentCard;
import by.ares.userservice.model.User;
import by.ares.userservice.repository.PaymentCardRepository;
import by.ares.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static by.ares.userservice.util.TestModelBuilder.buildPaymentCard;
import static by.ares.userservice.util.TestModelBuilder.buildUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserCardControllerTest extends AbstractIntegrationTest {

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
        mockMvc.perform(get("/users/{userId}/payment-cards", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

}