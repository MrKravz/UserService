package by.ares.userservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCardRequest {

    @NotBlank(message = "Card number must not be empty")
    @Size(min = 16, max = 16, message = "Card number must be 16 characters")
    private String number;

    @Future(message = "Date must be in the future")
    private LocalDate expirationDate;

    @NotNull(message = "User id must not be null")
    @Positive(message = "User id must be greater than 0 minutes")
    private Long userId;

}
