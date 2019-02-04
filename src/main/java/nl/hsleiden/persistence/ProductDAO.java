package nl.hsleiden.persistence;

import nl.hsleiden.database.MariaDB;
import nl.hsleiden.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private MariaDB database = MariaDB.getInstance();


    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        String selectString = "SELECT * FROM Product;";

        PreparedStatement preparedStatement;
        ResultSet result = null;
        try {
            preparedStatement = database.getConnection().prepareStatement(selectString);
            result = preparedStatement.executeQuery(selectString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (result != null && result.next()) {
                Product product = new Product(
                        result.getString("name"),
                        result.getDouble("price"),
                        this.getIngredientsOfProduct(result.getString("name")),
                        result.getString("picture")
                        );
                products.add(product);
            }

            database.closeResult(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private List<String> getIngredientsOfProduct(String productName) {
        List<String> ingredients = new ArrayList<>();

        String selectString = "SELECT ingredient_name FROM Ingredients " +
                "WHERE product_name = ?";

        PreparedStatement preparedStatement;
        ResultSet result = null;
        try {
            preparedStatement = database.getConnection().prepareStatement(selectString);
            preparedStatement.setString(1, productName);

            result = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (result != null && result.next()) {
                ingredients.add(result.getString("ingredient_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredients;
    }

    public void addProduct(Product product) {
        String insertString = "INSERT INTO Product " +
                "(name, price, picture) VALUES " +
                "(?, ?, ?);";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertString);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getPicture());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.addIngredientsOfProduct(product);
    }

    private void addIngredientsOfProduct(Product product) {
        for (String ingredient : product.getIngredients()) {
            String insertString = "INSERT INTO Ingredients " +
                    "(ingredient_name, product_name) VALUES " +
                    "(?, ?);";

            try {
                PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertString);
                preparedStatement.setString(1, ingredient);
                preparedStatement.setString(2, product.getName());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteProduct(String name) {
        this.deleteIngredientsOfProduct(name);

        String deleteString = "DELETE FROM Product WHERE name = ?";

        database.delete(name, deleteString);
    }

    private void deleteIngredientsOfProduct(String product_name) {
        String deleteString = "DELETE FROM Ingredients WHERE product_name = ?";

        database.delete(product_name, deleteString);
    }

    public void updateProduct(Product product, String name) {
        String updateString = "UPDATE Product " +
                "SET name = ?, price = ?, picture = ? " +
                "WHERE name = ?;";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(updateString);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getPicture());
            preparedStatement.setString(4, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
