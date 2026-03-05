package by.ares.userservice.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class SpecificationRequest {
    private String name;
    private String surname;
}
