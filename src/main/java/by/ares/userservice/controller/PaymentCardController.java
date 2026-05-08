package by.ares.userservice.controller;

import by.ares.userservice.dto.request.ActivationStatusRequest;
import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.service.PaymentCardService;
import by.ares.userservice.service.SecurityValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-cards")
public class PaymentCardController {

    private final PaymentCardService paymentCardService;
    private final SecurityValidationService securityValidationService;

    @GetMapping
    public ResponseEntity<Page<PaymentCardDto>> findAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(paymentCardService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentCardDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentCardService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Long> save(@Valid @RequestBody PaymentCardRequest paymentCardRequest,
                                     @RequestHeader("X-User-Id") Long userId,
                                     @RequestHeader("X-User-Role") String role) {
        securityValidationService.validateAccess(paymentCardRequest.getUserId(), userId, role);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentCardService.save(paymentCardRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@Valid @RequestBody PaymentCardRequest paymentCardRequest,
                                       @PathVariable Long id,
                                       @RequestHeader("X-User-Id") Long userId,
                                       @RequestHeader("X-User-Role") String role) {
        securityValidationService.validateAccess(paymentCardRequest.getUserId(), userId, role);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(paymentCardService.update(paymentCardRequest, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> changeStatus(@Valid @RequestBody ActivationStatusRequest activationStatusRequest,
                                             @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(paymentCardService.changeStatus(id, activationStatusRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentCardService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
