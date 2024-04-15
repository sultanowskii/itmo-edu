package web.lab4.server.service.auth;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import web.lab4.server.model.User;
import web.lab4.server.service.user.UserService;
import web.lab4.server.util.Crypto;

@Stateless
public class AuthService {

    @EJB
    UserService userService;
    @EJB
    JWTService jwtService;

    public AuthResult registerUser(String username, String rawPassword) {
        User user = new User(username, Crypto.hash(rawPassword));

        if (!userService.createUser(user)) {
            return AuthResult.error("User with such username already exists");
        }

        String token = jwtService.createToken(username);

        return AuthResult.success(token);
    }

    public AuthResult login(String username, String rawPassword) {
        if (!userService.isUserWithPasswordExist(username, Crypto.hash(rawPassword))) {
            return AuthResult.error("User with provided username and password doesn't exist");
        }

        String token = jwtService.createToken(username);

        return AuthResult.success(token);
    }
}
