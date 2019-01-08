package nl.hsleiden.service;

import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;

import javax.inject.Inject;
import java.util.Collection;

public class UserService extends BaseService {

    private final UserDAO dao;

    @Inject
    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public Collection<User> getAll() {
        //TODO: Connect to dao
        return null;
    }

    public User get(String email) {
        return dao.getByEmailAddress(email);
    }

    public void add(User user) {
        user.setRoles(new String[] { "GUEST" });

        //TODO: Connect to dao
    }

    public void update(User authenticator, String email, User user) {
        User oldUser = get(email);

        if (!authenticator.hasRole("ADMIN")) {
            assertSelf(authenticator, user);
        }

        //TODO: Connect to dao
    }

    public void delete(String email) {
        User user = get(email);

        //TODO: Connect to dao
    }


}
