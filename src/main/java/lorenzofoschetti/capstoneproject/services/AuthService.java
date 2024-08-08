package lorenzofoschetti.capstoneproject.services;

import lorenzofoschetti.capstoneproject.entities.User;
import lorenzofoschetti.capstoneproject.exceptions.UnauthorizedException;
import lorenzofoschetti.capstoneproject.payloads.UserLoginPayload;
import lorenzofoschetti.capstoneproject.payloads.UserLoginResponsePayload;
import lorenzofoschetti.capstoneproject.security.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JwtTools jwtTools;


    public UserLoginResponsePayload authenticateUserAndGenerateToken(UserLoginPayload payload) {

        User user = this.userService.findByEmail(payload.email());

        if (bcrypt.matches(payload.password(), user.getPassword())) {

            String token = jwtTools.createToken(user);
            return new UserLoginResponsePayload(token, user.getRole());
        } else {
            // 3. Se le credenziali sono errate --> 401 (Unauthorized)
            throw new UnauthorizedException("Credenziali non corrette!");
        }
    }
}
