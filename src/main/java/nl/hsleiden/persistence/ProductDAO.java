package nl.hsleiden.persistence;

import nl.hsleiden.MariaDB;
import nl.hsleiden.model.Product;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private MariaDB database = MariaDB.getInstance();
    private ProductIngredientsDAO productIngredientsDAO;

    @Inject
    public ProductDAO(ProductIngredientsDAO productIngredientsDAO) {
        this.productIngredientsDAO = productIngredientsDAO;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();

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
            while (result.next()) {
                Product product = new Product(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getDouble("price"),
                        result.getString("picture"),
                        this.productIngredientsDAO.getIngredientsOfProduct(result.getInt("id")));
                products.add(product);
            }

            database.closeResult(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
