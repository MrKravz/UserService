package by.ares.userservice.dto.response;

import by.ares.userservice.model.ActivationStatus;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Builder
@Accessors(chain = true)
public class PaymentCardDto {

    private Long id;

    private String number;

    private String holder;

    private LocalDate expirationDate;

    private ActivationStatus active;

}
