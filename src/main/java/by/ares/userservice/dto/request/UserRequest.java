package by.ares.userservice.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Builder
@Accessors(chain = true)
public class UserRequest {

    private String name;

    private String surname;

    private LocalDate birthDate;

    private String email;

}
