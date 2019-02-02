package nl.hsleiden.persistence;

import nl.hsleiden.MariaDB;
import nl.hsleiden.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private MariaDB database = MariaDB.getInstance();

    /**
     * Retrieves all Users from the database.
     * @return List User
     * @author Sven van Duijn
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String selectString = "SELECT * FROM User;";

        PreparedStatement preparedStatement;
        ResultSet result = null;
        try {
            preparedStatement = database.getConnection().prepareStatement(selectString);
            result = preparedStatement.executeQuery(selectString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (result.next()) {
                User user = new User(
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("phone"),
                        result.getString("password"),
                        result.getString("postcode"),
                        result.getString("street"),
                        result.getString("city"),
                        result.getString("role")
                );
                users.add(user);
            }

            database.closeResult(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Adds a User to the database.
     * @param user User
     * @author Sven van Duijn
     */
    public void addUser(User user) {
        String insertString = "INSERT INTO User " +
                "(name, email, phone, password, postcode, street, city, role) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertString);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPostcode());
            preparedStatement.setString(6, user.getStreet());
            preparedStatement.setString(7, user.getCity());
            preparedStatement.setString(8, user.getRole());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a User from the database using his/her userEmail.
     * @param userEmail String
     * @return User
     * @author Sven van Duijn
     */
    public User getUser(String userEmail) {

        String selectString = "SELECT * FROM User WHERE email = ?;";

        ResultSet result;

        result = database.getResult(userEmail, selectString);

        User user = null;
        try {
            if (result.next()) {
                user = new User(
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("phone"),
                        result.getString("password"),
                        result.getString("postcode"),
                        result.getString("street"),
                        result.getString("city"),
                        result.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Updates a User in the database.
     * @param user User
     * @author Sven van Duijn
     */
    public void updateUser(User user , String email) {
        String updateString = "UPDATE User " +
                "SET name = ?, email = ?, phone = ?, password = ?, postcode = ?, street = ?, city = ?, role = ?" +
                "WHERE email = ?;";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(updateString);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPostcode());
            preparedStatement.setString(6, user.getStreet());
            preparedStatement.setString(7, user.getCity());
            preparedStatement.setString(8, user.getRole());
            preparedStatement.setString(9, email);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a User from the database using his/her userEmail.
     * @param userEmail String
     * @author Sven van Duijn
     */
    public void deleteUser(String userEmail) {
        String deleteString = "DELETE FROM User " +
                "WHERE email = ?;";

        database.delete(userEmail, deleteString);
    }
}
