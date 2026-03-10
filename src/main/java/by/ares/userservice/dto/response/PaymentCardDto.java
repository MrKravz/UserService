package by.ares.userservice.dto.response;

import by.ares.userservice.model.ActivationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCardDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String number;

    private String holder;

    private LocalDate expirationDate;

    private ActivationStatus active;

}
