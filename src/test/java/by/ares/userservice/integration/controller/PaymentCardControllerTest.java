package by.ares.userservice.integration.controller;

import by.ares.userservice.dto.request.ActivationStatusRequest;
import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.integration.controller.abstraction.AbstractIntegrationTest;
import by.ares.userservice.model.PaymentCard;
import by.ares.userservice.model.User;
import by.ares.userservice.repository.PaymentCardRepository;
import by.ares.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static by.ares.userservice.util.TestConstants.*;
import static by.ares.userservice.util.TestModelBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PaymentCardControllerTest extends AbstractIntegrationTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PaymentCardRepository paymentCardRepository;

    private User user;
    private PaymentCard paymentCard;


    @BeforeEach
    void init() {
        user = saveTestUser();
        paymentCard = savePaymentCard(user);
    }

    private User saveTestUser() {
        return userRepository.save(buildUser());
    }

    private PaymentCard savePaymentCard(User user) {
        return paymentCardRepository.save(buildPaymentCard(user));
    }


    @Test
    void shouldFindPaymentCardById() throws Exception {
        mockMvc.perform(get("/payment_cards/{id}", paymentCard.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paymentCard.getId()))
                .andExpect(jsonPath("$.holder").value(CARD_HOLDER))
                .andExpect(jsonPath("$.expirationDate").value(String.valueOf(EXPIRATION_DATE)));
    }

    @Test
    void shouldCreatePaymentCard() throws Exception {
        PaymentCardRequest request = buildPaymentCardRequest(CARD_NUMBER_2, user.getId());
        String response = mockMvc.perform(post("/payment_cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Long paymentCardId = Long.valueOf(response);
        mockMvc.perform(get("/payment_cards/{id}", paymentCardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paymentCardId));
    }

    @Test
    void shouldUpdatePaymentCard() throws Exception {
        PaymentCardRequest request = buildPaymentCardRequest(CARD_NUMBER_3, user.getId());
        request.setExpirationDate(UPDATED_EXPIRATION_DATE);
        String response = mockMvc.perform(put("/payment_cards/{id}", paymentCard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(content().string(String.valueOf(paymentCard.getId())))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Long paymentCardId = Long.valueOf(response);
        mockMvc.perform(get("/payment_cards/{id}", paymentCardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(CARD_NUMBER_3));
    }

    @Test
    void shouldChangePaymentCardStatus() throws Exception {
        mockMvc.perform(put("/payment_cards/change_status/{id}", paymentCard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ActivationStatusRequest(INACTIVE))))
                .andExpect(status().isAccepted())
                .andExpect(content().string(String.valueOf(paymentCard.getId())));
        mockMvc.perform(get("/payment_cards/{id}", paymentCard.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeletePaymentCard() throws Exception {
        mockMvc.perform(delete("/payment_cards/{id}", paymentCard.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnPage() throws Exception {
        savePaymentCard(user);
        savePaymentCard(user);
        mockMvc.perform(get("/payment_cards?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

}