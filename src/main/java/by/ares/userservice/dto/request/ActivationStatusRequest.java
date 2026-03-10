package by.ares.userservice.dto.request;

import by.ares.userservice.model.ActivationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ActivationStatusRequest {

    private ActivationStatus activationStatus;

}
