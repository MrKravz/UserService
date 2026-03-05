package by.ares.userservice.service.abstraction;

import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface PaymentCardService extends CrudService<PaymentCardRequest, PaymentCardDto, Long>, StatusChangerService<Long> {

    Page<PaymentCardDto> findAll(Pageable pageable);
    Set<PaymentCardDto> findAllCardsOfUser(Long userId);

}
