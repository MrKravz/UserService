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
public class UserRequest {

    @NotBlank(message = "Name must not be empty")
    @Size(min = 2, max = 15, message = "Name must be at least 2 and not most than 15 characters")
    private String name;

    @NotBlank(message = "Surname must not be empty")
    @Size(min = 3, max = 15, message = "Surname must be at least 3 and not most than 15 characters")
    private String surname;

    @Past(message = "Date must be in the past")
    private LocalDate birthDate;

    @Email(message = "Must be email")
    @NotBlank(message = "Email must not be empty")
    private String email;

}
