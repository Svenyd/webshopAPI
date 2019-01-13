package nl.hsleiden.persistence;

import nl.hsleiden.MariaDB;
import nl.hsleiden.model.Ingredient;
import nl.hsleiden.model.User;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductIngredientsDAO {

    private MariaDB database = MariaDB.getInstance();
    private IngredientDAO ingredientDAO;

    @Inject
    public ProductIngredientsDAO(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }

    public Ingredient[] getIngredientsOfProduct(int productId) {

        String selectString = "SELECT * FROM Product_Ingredients WHERE product_id = ?;";

        ResultSet result;

        result = database.getResultInt(productId, selectString);

        List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
        try {
            while (result.next()) {
                ingredientsList.add(
                    this.ingredientDAO.getIngredient(result.getInt("ingredient_id"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Ingredient[] ingredients = new Ingredient[ingredientsList.size()];
        ingredients = ingredientsList.toArray(ingredients);
        return ingredients;
    }
}