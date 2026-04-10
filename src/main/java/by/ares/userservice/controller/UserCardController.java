package by.ares.userservice.controller;

import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.service.UserCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/payment-cards")
public class UserCardController {

    private final UserCardService userCardService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<PaymentCardDto>> findAll(@PathVariable Long userId) {
        return ResponseEntity.ok(userCardService.findAllByUserId(userId));
    }

}
