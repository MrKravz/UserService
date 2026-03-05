package by.ares.userservice.service.abstraction;

import by.ares.userservice.dto.request.UserRequest;
import by.ares.userservice.dto.response.UserDto;
import by.ares.userservice.model.User;

public interface UserService extends CrudService<UserRequest, UserDto, Long>,
        SpecificationService<UserDto, User>, StatusChangerService<Long> {
}
