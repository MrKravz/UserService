package by.ares.userservice.service.abstraction;

import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.model.PaymentCard;

import java.util.Set;

public interface PaymentCardService extends CrudService<PaymentCardRequest, PaymentCardDto, Long>,
        SpecificationService<PaymentCardDto, PaymentCard>, StatusChangerService<Long>{
    Set<PaymentCardDto> findAllCardsOfUser(Long userId);
}
