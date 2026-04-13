package by.ares.userservice.controller;

import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.service.SecurityValidationService;
import by.ares.userservice.service.UserCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/payment-cards")
public class UserCardController {

    private final UserCardService userCardService;
    private final SecurityValidationService securityValidationService;

    @GetMapping
    public ResponseEntity<List<PaymentCardDto>> findAll(@PathVariable(name = "userId") Long id,
                                                        @RequestHeader("X-User-Id") Long userId,
                                                        @RequestHeader("X-User-Role") String role) {
        securityValidationService.validateAccess(id, userId, role);
        return ResponseEntity.ok(userCardService.findAllByUserId(id));
    }

}
