package nl.hsleiden;

import java.sql.*;

public class MariaDB {

    private static MariaDB instance;
    private Connection connection;


    /**
     * Returns the instance of nl.hsleiden.MariaDB so it can be accessed anywhere.
     * @return nl.hsleiden.MariaDB
     * @author Sven van Duijn
     */
    public static MariaDB getInstance() {
        if (instance == null) {
            instance = new MariaDB();
        }

        return instance;
    }

    /**
     * Returns the connection
     * @return Connection
     * @author Sven van Duijn
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Returns if the instance of nl.hsleiden.MariaDB has a connection with the database.
     * @return boolean
     * @author Sven van Duijn
     */
    private boolean hasConnection() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Create a connection.
     * @param host String
     * @param dbName String
     * @param user String
     * @param password String
     * @author Sven van Duijn
     */
    public void connect(String host, String dbName, String user, String password) {
        String connectionString = String.format(
                "jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=UTC",
                host, dbName, user, password
        );
        connect(connectionString);
    }

    /**
     * Create a connection.
     * @param connectionString String
     * @author Sven van Duijn
     */
    private void connect(String connectionString) {
        if (hasConnection()) {
            return;
        }

        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the connection.
     * @author Sven van Duijn
     */
    public void disconnect() {
        if (!hasConnection()) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    /**
     * Returns a result using a parameter and a select String.
     * @param parameter String
     * @param selectString String
     * @return ResultSet
     * @author Sven van Duijn
     */
    public ResultSet getResult(String parameter, String selectString) {
        ResultSet result = null;
        try {
            PreparedStatement preparedStatement = instance.getConnection().prepareStatement(selectString);
            preparedStatement.setString(1, parameter);

            result = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Delete an object from the database.
     * @param parameter String
     * @param deleteString String
     * @author Sven van Duijn.
     */
    public void delete(String parameter, String deleteString) {
        try {
            PreparedStatement preparedStatement = instance.getConnection().prepareStatement(deleteString);
            preparedStatement.setString(1, parameter);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the result.
     * @param result ResultSet
     * @author Sven van Duijn
     */
    public void closeResult(ResultSet result) {
        Statement statement = null;
        try {
            statement = result.getStatement();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}