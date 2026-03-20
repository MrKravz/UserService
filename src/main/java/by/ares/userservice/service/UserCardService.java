package by.ares.userservice.service;

import by.ares.userservice.dto.response.PaymentCardDto;

import java.util.List;

public interface UserCardService {
    List<PaymentCardDto> findAllByUserId(Long id);
}
