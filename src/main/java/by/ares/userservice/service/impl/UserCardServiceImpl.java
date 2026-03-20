package by.ares.userservice.service.impl;

import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.mapper.PaymentCardMapper;
import by.ares.userservice.repository.PaymentCardRepository;
import by.ares.userservice.service.UserCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class UserCardServiceImpl implements UserCardService {

    private final PaymentCardRepository paymentCardRepository;
    private final PaymentCardMapper paymentCardMapper;

    @Override
    public List<PaymentCardDto> findAllByUserId(Long id) {
        return paymentCardRepository.findAllByUserId(id)
                .stream()
                .map(paymentCardMapper::toDto)
                .toList();
    }

}
