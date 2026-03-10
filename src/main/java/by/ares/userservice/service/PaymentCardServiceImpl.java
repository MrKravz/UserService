package by.ares.userservice.service;

import by.ares.userservice.dto.request.ActivationStatusRequest;
import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.exception.PaymentCardNotFoundException;
import by.ares.userservice.exception.UserCardLimitExceededException;
import by.ares.userservice.exception.UserNotFoundException;
import by.ares.userservice.mapper.PaymentCardMapper;
import by.ares.userservice.repository.PaymentCardRepository;
import by.ares.userservice.repository.UserRepository;
import by.ares.userservice.service.abstraction.PaymentCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
    private final UserRepository userRepository;
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
        return paymentCardMapper.toDto(paymentCardRepository.findById(id)
                .orElseThrow(() -> new PaymentCardNotFoundException("Payment with id: " + id + " not found")));
    }

    @Override
    @Transactional
    public Long save(PaymentCardRequest paymentCardRequest) {
        var owner = userRepository.findById(paymentCardRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + paymentCardRequest.getUserId() + " not found"));
        if (owner.getPaymentCards().size() >= 5) { throw new UserCardLimitExceededException("User can't have more than 5 cards"); }
        var card = paymentCardMapper.toModel(paymentCardRequest);
        card.setUser(owner);
        owner.addCard(card);
        card.setHolder(owner.getName().toUpperCase() + " " + owner.getSurname().toUpperCase());
        return paymentCardRepository.save(card).getId();
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", key = "'user:' + #id")
    public Long update(PaymentCardRequest paymentCardRequest, Long id) {
        var paymentCard = paymentCardRepository.findById(id)
                .orElseThrow(() -> new PaymentCardNotFoundException("Payment with id: " + id + " not found"));
        paymentCard.setNumber(paymentCardRequest.getNumber())
                .setExpirationDate(paymentCardRequest.getExpirationDate());
        return paymentCardRepository.save(paymentCard).getId();
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", key = "'user:' + #id")
    public void deleteById(Long id) {
        var paymentCard = paymentCardRepository.findById(id)
                .orElseThrow(() -> new PaymentCardNotFoundException("Payment with id: " + id + " not found"));
        paymentCard.getUser().removeCard(paymentCard);
        paymentCardRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", key = "'user:' + #id")
    public Long changeStatus(Long id, ActivationStatusRequest activationStatusRequest) {
        var user = paymentCardRepository.findById(id)
                .orElseThrow(() -> new PaymentCardNotFoundException("Payment with id: " + id + " not found"));
        user.setActive(activationStatusRequest.getActivationStatus());
        return paymentCardRepository.save(user).getId();
    }

}
