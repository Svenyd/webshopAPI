package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.security.Principal;
import java.util.Objects;

public class User implements Principal {

    @Length(max = 100)
    @JsonView(View.Public.class)
    private String name;

    @Email
    @Length(max = 100)
    @JsonView(View.Public.class)
    private String email;

    @Length(min = 10, max = 13)
    @JsonView(View.Public.class)
    private String phone;

    @Length(max = 100)
    @JsonView(View.Private.class)
    private String password;

    @Length(min = 6, max = 7)
    @JsonView(View.Public.class)
    private String postcode;

    @Length(max = 100)
    @JsonView(View.Public.class)
    private String street;

    @Length(max = 100)
    @JsonView(View.Public.class)
    private String city;

    @JsonView(View.Protected.class)
    private String role;

    public User() {
    }

    public User(String name, String email, String phone, String password, String postcode, String street, String city, String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.postcode = postcode;
        this.street = street;
        this.city = city;
        this.role = role;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean hasRole(String roleName) {
        return this.role.equals(roleName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(password, user.password) &&
                Objects.equals(postcode, user.postcode) &&
                Objects.equals(street, user.street) &&
                Objects.equals(city, user.city) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, email, phone, password, postcode, street, city, role);
    }
}
