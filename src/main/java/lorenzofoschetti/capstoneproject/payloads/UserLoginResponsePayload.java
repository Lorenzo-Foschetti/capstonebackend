package lorenzofoschetti.capstoneproject.payloads;

import lorenzofoschetti.capstoneproject.enums.Role;

public record UserLoginResponsePayload(String accessToken, Role role) {
}
