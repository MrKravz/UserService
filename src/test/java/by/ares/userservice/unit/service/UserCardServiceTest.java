package by.ares.userservice.unit.service;

import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.mapper.PaymentCardMapper;
import by.ares.userservice.model.PaymentCard;
import by.ares.userservice.model.User;
import by.ares.userservice.repository.PaymentCardRepository;
import by.ares.userservice.service.impl.UserCardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static by.ares.userservice.util.TestConstants.USER_ID;
import static by.ares.userservice.util.TestModelBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCardServiceTest {
    @Mock
    private PaymentCardRepository paymentCardRepository;

    @Mock
    private PaymentCardMapper paymentCardMapper;

    @InjectMocks
    private UserCardServiceImpl userCardService;

    private PaymentCard card;
    private PaymentCardDto cardDto;


    @BeforeEach
    void init() {
        User user = buildUser();
        card = buildPaymentCard(user);
        cardDto = buildPaymentCardDto();
    }

    @Test
    void findAllCardsOfUser_shouldReturnCards() {
        when(paymentCardRepository.findAllByUserId(USER_ID)).thenReturn(List.of(card));
        when(paymentCardMapper.toDto(card)).thenReturn(cardDto);
        List<PaymentCardDto> result = userCardService.findAllByUserId(USER_ID);
        assertEquals(1, result.size());
        verify(paymentCardRepository).findAllByUserId(USER_ID);
    }

}
