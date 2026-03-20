package by.ares.userservice.unit.service;

import by.ares.userservice.dto.request.ActivationStatusRequest;
import by.ares.userservice.dto.request.UserRequest;
import by.ares.userservice.dto.response.UserDto;
import by.ares.userservice.mapper.UserMapper;
import by.ares.userservice.model.User;
import by.ares.userservice.repository.UserRepository;
import by.ares.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Optional;

import static by.ares.userservice.util.TestConstants.*;
import static by.ares.userservice.util.TestModelBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private Cache cache;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;
    private UserRequest userRequest;


    @BeforeEach
    void init() {
        user = buildUser()
                .setId(USER_ID);
        userDto = buildUserDto();
        userRequest = buildUserRequest(USER_EMAIL);
        lenient().when(cacheManager.getCache(anyString())).thenReturn(cache);
    }


    @Test
    void findById_shouldReturnUserDto() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);
        var result = userService.findById(USER_ID);
        assertEquals(USER_ID, result.getId());
        verify(userRepository).findById(USER_ID);
    }

    @Test
    void save_shouldSaveUser() {
        when(userMapper.toModel(userRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        var result = userService.save(userRequest);
        assertEquals(USER_ID, result);
        verify(userRepository).save(user);
    }

    @Test
    void update_shouldUpdateUser() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        var result = userService.update(userRequest, USER_ID);
        assertEquals(USER_ID, result);
        verify(userRepository).save(user);
    }

    @Test
    void deleteById_shouldDeleteUser() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        userService.deleteById(USER_ID);
        verify(userRepository).findById(USER_ID);
        verify(userRepository).save(user);
    }

    @Test
    void changeStatus_shouldUpdateStatus() {
        when(userRepository.findAnyById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        var result = userService.changeStatus(USER_ID, new ActivationStatusRequest(INACTIVE));
        assertEquals(USER_ID, result);
        verify(userRepository).save(user);
    }

}
