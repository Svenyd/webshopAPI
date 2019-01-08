package nl.hsleiden.persistence;

import nl.hsleiden.model.User;

public class UserDAO {

    public User getByEmailAddress(String email) {
        return new User("test@test.com", "Test", "p@ssw0rd");
    }
}
