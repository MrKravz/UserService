package by.ares.userservice.service;

import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.mapper.PaymentCardMapper;
import by.ares.userservice.model.PaymentCard;
import by.ares.userservice.repository.PaymentCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static by.ares.userservice.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentCardServiceTest {

    @Mock
    private PaymentCardRepository paymentCardRepository;
    @Mock
    private PaymentCardMapper paymentCardMapper;
    @InjectMocks
    private PaymentCardServiceImpl paymentCardService;

    private PaymentCard card;
    private PaymentCardDto cardDto;
    private PaymentCardRequest request;

    @BeforeEach
    void setUp() {
        card = new PaymentCard()
                .setId(CARD_ID)
                .setNumber(CARD_NUMBER)
                .setHolder(CARD_HOLDER)
                .setExpirationDate(EXPIRATION_DATE);
        cardDto = PaymentCardDto.builder()
                .id(CARD_ID)
                .build();
        request = PaymentCardRequest.builder()
                .number(CARD_NUMBER)
                .holder(CARD_HOLDER)
                .expirationDate(EXPIRATION_DATE)
                .build();
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
        when(paymentCardRepository.save(card)).thenReturn(card);
        when(paymentCardMapper.toDto(card)).thenReturn(cardDto);
        var result = paymentCardService.save(request);
        assertEquals(CARD_ID, result.getId());
        verify(paymentCardRepository).save(card);
    }

    @Test
    void update_shouldUpdateCard() {
        when(paymentCardRepository.findById(CARD_ID)).thenReturn(Optional.of(card));
        when(paymentCardRepository.save(card)).thenReturn(card);
        when(paymentCardMapper.toDto(card)).thenReturn(cardDto);
        var result = paymentCardService.update(request, CARD_ID);
        assertEquals(CARD_ID, result.getId());
        verify(paymentCardRepository).save(card);
    }

    @Test
    void deleteById_shouldDeleteCard() {
        paymentCardService.deleteById(CARD_ID);
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
        Long result = paymentCardService.changeStatus(CARD_ID, ACTIVE);
        assertEquals(CARD_ID, result);
        verify(paymentCardRepository).save(card);
    }

}
