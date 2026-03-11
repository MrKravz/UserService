package by.ares.userservice.unit.service;

import by.ares.userservice.dto.request.ActivationStatusRequest;
import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.mapper.PaymentCardMapper;
import by.ares.userservice.model.ActivationStatus;
import by.ares.userservice.model.PaymentCard;
import by.ares.userservice.model.User;
import by.ares.userservice.repository.PaymentCardRepository;
import by.ares.userservice.repository.UserRepository;
import by.ares.userservice.service.PaymentCardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static by.ares.userservice.util.TestConstants.*;
import static by.ares.userservice.util.TestModelBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentCardServiceTest {

    @Mock
    private PaymentCardRepository paymentCardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PaymentCardMapper paymentCardMapper;
    @InjectMocks
    private PaymentCardServiceImpl paymentCardService;

    private PaymentCard card;
    private PaymentCardDto cardDto;
    private PaymentCardRequest request;
    private User user;


    @BeforeEach
    void init() {
        user = buildUser()
                .setId(USER_ID);
        card = buildPaymentCard(user)
                .setId(CARD_ID)
                .setNumber(CARD_NUMBER);
        cardDto = buildPaymentCardDto();
        request = buildPaymentCardRequest(CARD_NUMBER, USER_ID);
        user.addCard(card);
    }


    @Test
    void findById_shouldReturnCard() {
        when(paymentCardRepository.findById(CARD_ID)).thenReturn(Optional.of(card));
        when(paymentCardMapper.toDto(card)).thenReturn(cardDto);
        PaymentCardDto result = paymentCardService.findById(CARD_ID);
        assertEquals(CARD_ID, result.getId());
        verify(paymentCardRepository).findById(CARD_ID);
    }

    @Test
    void save_shouldSaveCard() {
        when(paymentCardMapper.toModel(request)).thenReturn(card);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(paymentCardRepository.save(card)).thenReturn(card);
        var result = paymentCardService.save(request);
        assertEquals(CARD_ID, result);
        verify(paymentCardRepository).save(card);
    }

    @Test
    void update_shouldUpdateCard() {
        when(paymentCardRepository.findById(CARD_ID)).thenReturn(Optional.of(card));
        when(paymentCardRepository.save(card)).thenReturn(card);
        var result = paymentCardService.update(request, CARD_ID);
        assertEquals(CARD_ID, result);
        verify(paymentCardRepository).save(card);
    }

    @Test
    void deleteById_shouldDeleteCard() {
        when(paymentCardRepository.findById(CARD_ID)).thenReturn(Optional.of(card));
        paymentCardService.deleteById(CARD_ID);
        verify(paymentCardRepository).findById(CARD_ID);
        verify(paymentCardRepository).deleteById(CARD_ID);
    }

    @Test
    void findAllCardsOfUser_shouldReturnCards() {
        when(paymentCardRepository.findAllByUserId(USER_ID)).thenReturn(Set.of(card));
        when(paymentCardMapper.toDto(card)).thenReturn(cardDto);
        Set<PaymentCardDto> result = paymentCardService.findAllCardsOfUser(USER_ID);
        assertEquals(1, result.size());
        verify(paymentCardRepository).findAllByUserId(USER_ID);
    }

    @Test
    void changeStatus_shouldUpdateStatus() {
        when(paymentCardRepository.findById(CARD_ID)).thenReturn(Optional.of(card));
        when(paymentCardRepository.save(card)).thenReturn(card);
        Long result = paymentCardService.changeStatus(CARD_ID, new ActivationStatusRequest(ActivationStatus.INACTIVE));
        assertEquals(CARD_ID, result);
        verify(paymentCardRepository).save(card);
    }

}
