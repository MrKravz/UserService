package by.ares.userservice.controller;

import by.ares.userservice.dto.request.ActivationStatusRequest;
import by.ares.userservice.dto.request.SpecificationRequest;
import by.ares.userservice.dto.request.UserRequest;
import by.ares.userservice.dto.response.UserDto;
import by.ares.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(@ModelAttribute SpecificationRequest specificationRequest,
                                                 @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(Optional.of(specificationRequest), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Long> save(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@Valid @RequestBody UserRequest userRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.update(userRequest, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> changeStatus(@Valid @RequestBody ActivationStatusRequest activationStatusRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.changeStatus(id, activationStatusRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
