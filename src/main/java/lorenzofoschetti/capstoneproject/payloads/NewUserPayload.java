package lorenzofoschetti.capstoneproject.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUserPayload(

        @NotEmpty(message = "Il nome proprio è un campo obbligatorio!")
        String name,
        @NotEmpty(message = "Il cognome è un campo obbligatorio!")
        String surname,
        @NotEmpty
        @Email(message = "l'email inserita non è valida")
        String email,
        @NotEmpty(message = "La password è obbligatoria!")
        @Size(min = 6, message = "La password deve avere almeno sei caratteri!")
        String password
) {
}
