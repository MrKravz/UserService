package by.ares.userservice.service.abstraction;

import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.model.PaymentCard;

public interface PaymentCardService extends CrudService<PaymentCardRequest, PaymentCardDto, Long>,
        SpecificationService<PaymentCardDto, PaymentCard>, StatusChangerService<Long>{
}
