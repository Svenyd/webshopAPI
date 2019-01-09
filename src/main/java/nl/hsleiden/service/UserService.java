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

    public void add(User user) {
//        user.setRoles(new String[] { "GUEST" });

        dao.addUser(user);
    }

    public void update(User authenticator, String email, User user) {
//        if (!authenticator.hasRole("ADMIN")) {
//            assertSelf(authenticator, user);
//        }

        dao.updateUser(user, email);
    }

    public void delete(String email) {
        dao.deleteUser(email);
    }
}
