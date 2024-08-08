package lorenzofoschetti.capstoneproject.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserLoginPayload(
        @NotEmpty(message = "L'email è obbligatoria!")
        @Email
        String email,
        @NotEmpty(message = "L'email è obbligatoria")
        @Size(min = 6, message = "La password deve avere almeno 6 caratteri!")
        String password
) {
}
