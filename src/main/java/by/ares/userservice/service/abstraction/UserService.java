package by.ares.userservice.service.abstraction;

import by.ares.userservice.dto.request.UserRequest;
import by.ares.userservice.dto.response.UserDto;

public interface UserService extends SpecificationService<UserDto>, StatusChangerService<Long> {
    UserDto findById(Long id);
    Long save(UserRequest userRequest);
    Long update(UserRequest userRequest, Long id);
    void deleteById(Long id);
}
