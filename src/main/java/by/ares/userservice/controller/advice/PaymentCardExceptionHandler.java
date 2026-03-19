package by.ares.userservice.controller.advice;

import by.ares.userservice.exception.ExceptionResponse;
import by.ares.userservice.exception.PaymentCardNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentCardExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(PaymentCardExceptionHandler.class);

    @ExceptionHandler(value = PaymentCardNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlePaymentCardNotFoundException(PaymentCardNotFoundException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage(), System.currentTimeMillis()));
    }

}
