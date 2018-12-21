package service;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import model.User;
import persistence.UserDAO;

import javax.inject.Inject;
import java.util.Optional;

public class AuthenticationService implements Authenticator<BasicCredentials, User>, Authorizer<User> {

    private final UserDAO userDAO;

    @Inject
    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        User user = userDAO.getByEmailAddress(basicCredentials.getUsername());

        if (user != null && user.getPassword().equals(basicCredentials.getPassword())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    @Override
    public boolean authorize(User user, String roleName) {
        return user.hasRole(roleName);
    }
}
