package by.ares.userservice.mapper;

import by.ares.userservice.dto.request.UserRequest;
import by.ares.userservice.dto.response.UserDto;
import by.ares.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PaymentCardMapper.class})
public interface UserMapper extends SimpleMapper<UserRequest, UserDto, User> {
}
