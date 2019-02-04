package nl.hsleiden.service;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import nl.hsleiden.model.User;

import javax.inject.Inject;
import java.util.Optional;

public class AuthenticationService implements Authenticator<BasicCredentials, User>, Authorizer<User> {

    private final UserService userService;

    @Inject
    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) {
        User user = userService.get(basicCredentials.getUsername());

        if (user != null && PasswordHashingService.validatePassword(basicCredentials.getPassword(), user.getPassword())) {
            return Optional.of(userService.get(basicCredentials.getUsername()));
        }

        return Optional.empty();
    }



    @Override
    public boolean authorize(User user, String roleName) {
        return true;
    }
}
