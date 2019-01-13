package nl.hsleiden.persistence;

import nl.hsleiden.MariaDB;
import nl.hsleiden.model.Ingredient;
import nl.hsleiden.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientDAO {

    private MariaDB database = MariaDB.getInstance();

    public Ingredient getIngredient(int id) {

        String selectString = "SELECT * FROM Ingredient WHERE id = ?;";

        ResultSet result;

        result = database.getResultInt(id, selectString);

        Ingredient ingredient = null;
        try {
            if (result.next()) {
                ingredient = new Ingredient(
                        result.getInt("id"),
                        result.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredient;
    }
}
