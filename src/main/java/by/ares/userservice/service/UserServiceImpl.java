package by.ares.userservice.service;

import by.ares.userservice.dto.request.ActivationStatusRequest;
import by.ares.userservice.dto.request.SpecificationRequest;
import by.ares.userservice.dto.request.UserRequest;
import by.ares.userservice.dto.response.UserDto;
import by.ares.userservice.exception.UserInvalidDataException;
import by.ares.userservice.exception.UserNotFoundException;
import by.ares.userservice.mapper.UserMapper;
import by.ares.userservice.model.User;
import by.ares.userservice.repository.UserRepository;
import by.ares.userservice.service.abstraction.SpecificationBuilderService;
import by.ares.userservice.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SpecificationBuilderService<User> specificationBuilderService;
    private final UserMapper userMapper;

    @Override
    public Page<UserDto> findAll(SpecificationRequest specificationRequest, Pageable pageable) {
        return specificationRequest == null ? userRepository.findAll(pageable).map(userMapper::toDto) :
                userRepository.findAll(specificationBuilderService.configure(specificationRequest), pageable)
                        .map(userMapper::toDto);
    }

    @Override
    @Cacheable(value = "users", key = "'user:' + #id", sync = true)
    public UserDto findById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found")));
    }

    @Override
    @Transactional
    public Long save(UserRequest userRequest) {
        if ((LocalDate.now().getYear() - userRequest.getBirthDate().getYear()) < 18) {
            throw new UserInvalidDataException("User not mature enough to own a card");
        }
        return userRepository.save(userMapper.toModel(userRequest)).getId();
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", key = "'user:' + #id")
    public Long update(UserRequest userRequest, Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
        if (!userRequest.getName().equals(user.getName()) || !userRequest.getSurname().equals(user.getSurname())) {
            for (var card : user.getPaymentCards()) {
                card.setHolder(userRequest.getName().toUpperCase() + " " + userRequest.getSurname().toUpperCase());
            }
        }
        user.setName(userRequest.getName())
                .setSurname(userRequest.getSurname())
                .setBirthDate(userRequest.getBirthDate())
                .setEmail(userRequest.getEmail());
        return userRepository.save(user).getId();
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", key = "'user:' + #id")
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", key = "'user:' + #id")
    public Long changeStatus(Long id, ActivationStatusRequest activationStatusRequest) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
        user.setActive(activationStatusRequest.getActivationStatus());
        return userRepository.save(user).getId();
    }

}
