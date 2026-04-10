package by.ares.userservice.controller;

import by.ares.userservice.dto.request.ActivationStatusRequest;
import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.service.PaymentCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-cards")
public class PaymentCardController {

    private final PaymentCardService paymentCardService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PaymentCardDto>> findAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(paymentCardService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PaymentCardDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentCardService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Long> save(@Valid @RequestBody PaymentCardRequest paymentCardRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentCardService.save(paymentCardRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Long> update(@Valid @RequestBody PaymentCardRequest paymentCardRequest,
                                       @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(paymentCardService.update(paymentCardRequest, id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> changeStatus(@Valid @RequestBody ActivationStatusRequest activationStatusRequest,
                                             @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(paymentCardService.changeStatus(id, activationStatusRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentCardService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
