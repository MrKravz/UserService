package by.ares.userservice.service;

import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.mapper.PaymentCardMapper;
import by.ares.userservice.model.ActivationStatus;
import by.ares.userservice.repository.PaymentCardRepository;
import by.ares.userservice.service.abstraction.PaymentCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class PaymentCardServiceImpl implements PaymentCardService {

    private final PaymentCardRepository paymentCardRepository;
    private final PaymentCardMapper paymentCardMapper;

    @Override
    public Page<PaymentCardDto> findAll(Pageable pageable) {
        return paymentCardRepository.findAll(pageable)
                .map(paymentCardMapper::toDto);
    }

    @Override
    public Set<PaymentCardDto> findAllCardsOfUser(Long userId) {
        return paymentCardRepository.findAllByUserId(userId)
                .stream()
                .map(paymentCardMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = "cards", key = "'card:' + #id", sync = true)
    public PaymentCardDto findById(Long id) {
        return paymentCardMapper.toDto(paymentCardRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    @CachePut(value = "cards", key = "'card:' + #result")
    public Long save(PaymentCardRequest paymentCardRequest) {
        return paymentCardRepository.save(paymentCardMapper.toModel(paymentCardRequest)).getId();
    }

    @Override
    @Transactional
    @CachePut(value = "cards", key = "'card:' + #id")
    public Long update(PaymentCardRequest paymentCardRequest, Long id) {
        var paymentCard = paymentCardRepository.findById(id).orElseThrow();
        paymentCard.setNumber(paymentCardRequest.getNumber())
                .setHolder(paymentCardRequest.getHolder())
                .setExpirationDate(paymentCardRequest.getExpirationDate());
        return paymentCardRepository.save(paymentCard).getId();
    }

    @Override
    @Transactional
    @CacheEvict(value = "cards", key = "'card:' + #id")
    public void deleteById(Long id) {
        paymentCardRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CachePut(value = "cards", key = "'card:' + #id")
    public Long changeStatus(Long id, ActivationStatus activationStatus) {
        var user = paymentCardRepository.findById(id).orElseThrow();
        user.setActive(activationStatus);
        return paymentCardRepository.save(user).getId();
    }

}
