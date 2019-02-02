package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Product {

    @NotEmpty
    @Length(max = 100)
    @JsonView(View.Public.class)
    private String name;

    @NotEmpty
    @JsonView(View.Public.class)
    private double price;

    @NotEmpty
    @JsonView(View.Public.class)
    private List<String> ingredients;

    @NotEmpty
    @Length(max = 200)
    @JsonView(View.Public.class)
    private String picture;

    public Product() {
    }

    public Product(String name, double price, List<String> ingredients, String picture) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Objects.equals(name, product.name) &&
                Objects.equals(ingredients, product.ingredients) &&
                Objects.equals(picture, product.picture);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, price, ingredients, picture);
    }
}
