package by.ares.userservice.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Builder
@Accessors(chain = true)
public class PaymentCardRequest {

    private String number;

    private String holder;

    private LocalDate expirationDate;

}
