package by.ares.userservice.dto.response;

import by.ares.userservice.model.ActivationStatus;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@Accessors(chain = true)
public class UserDto {

    private Long id;

    private String name;

    private String surname;

    private LocalDate birthDate;

    private String email;

    private ActivationStatus active;

    private Set<PaymentCardDto> paymentCards;

}
