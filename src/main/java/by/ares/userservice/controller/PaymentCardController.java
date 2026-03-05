package by.ares.userservice.controller;

import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.model.ActivationStatus;
import by.ares.userservice.service.abstraction.PaymentCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment_cards")
public class PaymentCardController {

    private final PaymentCardService paymentCardService;

    @GetMapping
    public ResponseEntity<Page<PaymentCardDto>> findAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(paymentCardService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<PaymentCardDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentCardService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody PaymentCardRequest paymentCardRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentCardService.save(paymentCardRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<Long> update(@RequestBody PaymentCardRequest paymentCardRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(paymentCardService.update(paymentCardRequest, id));
    }

    @PutMapping("/change_status/{id}")
    public ResponseEntity<Long> changeStatus(@RequestBody ActivationStatus activationStatus, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(paymentCardService.changeStatus(id, activationStatus));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentCardService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
