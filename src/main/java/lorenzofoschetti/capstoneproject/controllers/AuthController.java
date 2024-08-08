package lorenzofoschetti.capstoneproject.controllers;

import lorenzofoschetti.capstoneproject.exceptions.BadRequestException;
import lorenzofoschetti.capstoneproject.payloads.NewUserPayload;
import lorenzofoschetti.capstoneproject.payloads.NewUserResponsePayload;
import lorenzofoschetti.capstoneproject.payloads.UserLoginPayload;
import lorenzofoschetti.capstoneproject.payloads.UserLoginResponsePayload;
import lorenzofoschetti.capstoneproject.services.AuthService;
import lorenzofoschetti.capstoneproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService usersService;

    @PostMapping("/login")
    public UserLoginResponsePayload login(@RequestBody UserLoginPayload payload) {
        UserLoginResponsePayload authResponse = authService.authenticateUserAndGenerateToken(payload);
        return new UserLoginResponsePayload(authResponse.accessToken(), authResponse.role());
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponsePayload saveUser(@RequestBody @Validated NewUserPayload body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewUserResponsePayload(this.usersService.save(body).getId());
    }
}
