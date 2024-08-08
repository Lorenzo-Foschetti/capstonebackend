package lorenzofoschetti.capstoneproject.exceptions;

import lorenzofoschetti.capstoneproject.payloads.ErrorsPayload;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    // Nelle parentesi indico quale eccezione debba venir gestita da questo metodo
    // Questo metodo dovrà rispondere con 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsPayload handleBadRequest(BadRequestException ex) {
        if (ex.getErrorsList() != null) {
            // Se c'è la lista degli errori allora nel payload metterò la lista di messaggi di errore concatenati
            String message = ex.getErrorsList().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            return new ErrorsPayload(message, LocalDateTime.now());

        } else {
            // Se la lista degli errori è null mandiamo un classico payload di errore con semplice messaggio
            return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    // Questo metodo dovrà rispondere con 401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsPayload handleUnauthorized(UnauthorizedException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    // Questo metodo dovrà rispondere con 403
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorsPayload handleForbidden(AuthorizationDeniedException ex) {
        return new ErrorsPayload("Non hai accesso a questa funzionalità", LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    // Questo metodo dovrà rispondere con 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsPayload handleNotFound(NotFoundException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    // Questo metodo dovrà rispondere con 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsPayload handleGenericErrors(Exception ex) {
        ex.printStackTrace(); // Non dimentichiamoci che è ESTREMAMENTE UTILE sapere dove è stato causato l'errore per poterlo fixare!
        return new ErrorsPayload("Problema lato server! Giuro che lo risolveremo presto!", LocalDateTime.now());
    }
}
