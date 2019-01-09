package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.security.Principal;
import java.util.Objects;

public class User implements Principal {

    @NotEmpty
    @Length(min = 3, max = 100)
    @JsonView(View.Public.class)
    private String name;

    @NotEmpty
    @Length(min = 6, max = 7)
    @JsonView(View.Public.class)
    private String postcode;

    @NotEmpty
    @Length(min = 1, max = 10)
    @JsonView(View.Public.class)
    private String house_number;

    @NotEmpty
    @Email
    @JsonView(View.Public.class)
    private String email;

    @NotEmpty
    @Length(min = 8)
    @JsonView(View.Protected.class)
    private String password;

    @NotEmpty
    @JsonView(View.Public.class)
    private String city;

    public User(String name, String postcode, String house_number, String email, String password, String city) {
        this.name = name;
        this.postcode = postcode;
        this.house_number = house_number;
        this.email = email;
        this.password = password;
        this.city = city;
    }

    public User() {

    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void setRoles(String[] roles) {
//        this.roles = roles;
//    }
//
//    public boolean hasRole(String roleName) {
//        if (roles != null) {
//            for (String role : roles) {
//                return roleName.equals(role);
//            }
//        }
//        return false;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
