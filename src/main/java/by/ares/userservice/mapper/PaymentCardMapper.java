package by.ares.userservice.mapper;

import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.model.PaymentCard;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentCardMapper extends SimpleMapper<PaymentCardRequest, PaymentCardDto, PaymentCard> {
}
