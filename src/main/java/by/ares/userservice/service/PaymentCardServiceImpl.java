package by.ares.userservice.service;

import by.ares.userservice.dto.request.ActivationStatusRequest;
import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.exception.PaymentCardNotFoundException;
import by.ares.userservice.exception.UserCardLimitExceededException;
import by.ares.userservice.exception.UserNotFoundException;
import by.ares.userservice.mapper.PaymentCardMapper;
import by.ares.userservice.model.ActivationStatus;
import by.ares.userservice.model.PaymentCard;
import by.ares.userservice.model.User;
import by.ares.userservice.repository.PaymentCardRepository;
import by.ares.userservice.repository.UserRepository;
import by.ares.userservice.service.abstraction.PaymentCardService;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
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
    private final CacheManager cacheManager;

    private final String exceptionMessage = "Payment with this id not found";
    private final ActivationStatus deletedActivationStatus = ActivationStatus.INACTIVE;

    @Override
    public Page<PaymentCardDto> findAll(Pageable pageable) {
        return paymentCardRepository.findAll(pageable).map(paymentCardMapper::toDto);
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
                .orElseThrow(() -> new PaymentCardNotFoundException(exceptionMessage)));
    }

    @Override
    @Transactional
    @Retryable(
            retryFor = OptimisticLockException.class,
            backoff = @Backoff(delay = 100)
    )
    public Long save(PaymentCardRequest paymentCardRequest) {
        User owner = userRepository.findById(paymentCardRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + paymentCardRequest.getUserId() + " not found"));
        if (owner.getPaymentCards().size() >= 5) {
            throw new UserCardLimitExceededException("User can't have more than 5 cards");
        }
        PaymentCard card = paymentCardMapper.toModel(paymentCardRequest);
        card.setUser(owner);
        owner.addCard(card);
        card.setHolder(owner.getName().toUpperCase() + " " + owner.getSurname().toUpperCase());
        cacheManager.getCache("users").evict("user:" + owner.getId());
        return paymentCardRepository.save(card).getId();
    }

    @Override
    @Transactional
    public Long update(PaymentCardRequest paymentCardRequest, Long id) {
        PaymentCard paymentCard = paymentCardRepository.findById(id)
                .orElseThrow(() -> new PaymentCardNotFoundException(exceptionMessage));
        paymentCard.setNumber(paymentCardRequest.getNumber())
                .setExpirationDate(paymentCardRequest.getExpirationDate());
        cacheManager.getCache("cards").evict("card:" + paymentCard.getId());
        cacheManager.getCache("users").evict("user:" + paymentCard.getUser().getId());
        return paymentCardRepository.save(paymentCard).getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        PaymentCard paymentCard = paymentCardRepository.findById(id)
                .orElseThrow(() -> new PaymentCardNotFoundException(exceptionMessage));
        paymentCard.setActive(deletedActivationStatus);
        cacheManager.getCache("cards").evict("card:" + paymentCard.getId());
        cacheManager.getCache("users").evict("user:" + paymentCard.getUser().getId());
        paymentCard.getUser().removeCard(paymentCard);
        paymentCardRepository.save(paymentCard);
    }

    @Override
    @Transactional
    public Long changeStatus(Long id, ActivationStatusRequest activationStatusRequest) {
        PaymentCard paymentCard = paymentCardRepository.findById(id)
                .orElseThrow(() -> new PaymentCardNotFoundException(exceptionMessage));
        paymentCard.setActive(activationStatusRequest.getActivationStatus());
        cacheManager.getCache("cards").evict("card:" + paymentCard.getId());
        cacheManager.getCache("users").evict("user:" + paymentCard.getUser().getId());
        return paymentCardRepository.save(paymentCard).getId();
    }

}
