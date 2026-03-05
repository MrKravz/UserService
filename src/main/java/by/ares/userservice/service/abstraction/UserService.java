package by.ares.userservice.service.abstraction;

import by.ares.userservice.dto.request.UserRequest;
import by.ares.userservice.dto.response.UserDto;

public interface UserService extends CrudService<UserRequest, UserDto, Long>,
        SpecificationService<UserDto>, StatusChangerService<Long> {
}
