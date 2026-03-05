package by.ares.userservice.service;

import by.ares.userservice.dto.request.UserRequest;
import by.ares.userservice.dto.response.UserDto;
import by.ares.userservice.mapper.UserMapper;
import by.ares.userservice.model.ActivationStatus;
import by.ares.userservice.model.User;
import by.ares.userservice.repository.UserRepository;
import by.ares.userservice.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserDto> findAll(Specification<User> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable).map(userMapper::toDto);
    }

    @Override
    @Cacheable(value = "users", key = "'user:' + #id", sync = true)
    public UserDto findById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    @CachePut(value = "users", key = "'user:' + #result.id")
    public UserDto save(UserRequest userRequest) {
        return userMapper.toDto(userRepository.save(userMapper.toModel(userRequest)));
    }

    @Override
    @Transactional
    @CachePut(value = "users", key = "'user:' + #id")
    public UserDto update(UserRequest userRequest, Long id) {
        var user = userRepository.findById(id).orElseThrow();
        user.setName(userRequest.getName())
                .setSurname(userRequest.getSurname())
                .setBirthDate(userRequest.getBirthDate())
                .setEmail(userRequest.getEmail());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", key = "'user:' + #id")
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CachePut(value = "users", key = "'user:' + #id")
    public Long changeStatus(Long id, ActivationStatus activationStatus) {
        var user = userRepository.findById(id).orElseThrow();
        user.setActive(activationStatus);
        return userRepository.save(user).getId();
    }

}
