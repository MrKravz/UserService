package by.ares.userservice.service;

import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.mapper.PaymentCardMapper;
import by.ares.userservice.model.ActivationStatus;
import by.ares.userservice.model.PaymentCard;
import by.ares.userservice.repository.PaymentCardRepository;
import by.ares.userservice.service.abstraction.PaymentCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.DEFAULT)
public class PaymentCardServiceImpl implements PaymentCardService {

    private final PaymentCardRepository paymentCardRepository;
    private final PaymentCardMapper paymentCardMapper;


    @Override
    public Page<PaymentCardDto> findAll(Specification<PaymentCard> specification, Pageable pageable) {
        return paymentCardRepository.findAll(specification, pageable).map(paymentCardMapper::toDto);
    }

    @Override
    public PaymentCardDto findById(Long id) {
        return paymentCardMapper.toDto(paymentCardRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public PaymentCardDto save(PaymentCardRequest paymentCardRequest) {
        return paymentCardMapper.toDto(paymentCardRepository.save(paymentCardMapper.toModel(paymentCardRequest)));
    }

    @Override
    @Transactional
    public PaymentCardDto update(PaymentCardRequest paymentCardRequest, Long id) {
        var paymentCard = paymentCardRepository.findById(id).orElseThrow();
        paymentCard.setNumber(paymentCardRequest.getNumber())
                .setHolder(paymentCard.getHolder())
                .setExpirationDate(paymentCardRequest.getExpirationDate());
        return paymentCardMapper.toDto(paymentCardRepository.save(paymentCardMapper.toModel(paymentCardRequest)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        paymentCardRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Long changeStatus(Long id, ActivationStatus activationStatus) {
        var user = paymentCardRepository.findById(id).orElseThrow();
        user.setActive(activationStatus);
        return paymentCardRepository.save(user).getId();
    }


}
