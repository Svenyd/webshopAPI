package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Arrays;
import java.util.Objects;

public class Product {

    @NotEmpty
    @JsonView(View.Public.class)
    private int id;

    @NotEmpty
    @JsonView(View.Public.class)
    private String name;

    @NotEmpty
    @JsonView(View.Public.class)
    private double price;

    @NotEmpty
    @JsonView(View.Public.class)
    private String picture;

    @NotEmpty
    @JsonView(View.Public.class)
    private Ingredient[] ingredients;

    public Product(int id, String name, double price, String picture, Ingredient[] ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.ingredients = ingredients;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id &&
                price == product.price &&
                Objects.equals(name, product.name) &&
                Objects.equals(picture, product.picture) &&
                Arrays.equals(ingredients, product.ingredients);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, name, price, picture);
        result = 31 * result + Arrays.hashCode(ingredients);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", picture='" + picture + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                '}';
    }
}
