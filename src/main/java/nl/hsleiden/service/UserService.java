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
        return dao.getAllUsers();
    }

    public User get(String email) {
        return dao.getUser(email);
    }

    public User add(User user) {
        user.setRole("customer");

        if (dao.getUser(user.getEmail()) == null) {
            user.setPassword(PasswordHashingService.generateStrongPasswordHash(user.getPassword()));
            dao.addUser(user);
            return user;
        }
        return null;
    }

    public void update(User authenticator, String email, User user) {
        if (!authenticator.hasRole("admin")) {
            assertSelf(authenticator, user);
            user.setRole("customer");
        }

        dao.updateUser(user, email);
    }

    public void delete(String email) {
        dao.deleteUser(email);
    }
}
