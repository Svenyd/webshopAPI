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
    private String fullName;

    @NotEmpty
    @Length(min = 6, max = 7)
    @JsonView(View.Public.class)
    private String postcode;

    @NotEmpty
    @Length(min = 1, max = 10)
    @JsonView(View.Public.class)
    private String streetNumber;

    @NotEmpty
    @Email
    @JsonView(View.Public.class)
    private String emailAddress;

    @NotEmpty
    @Length(min = 8)
    @JsonView(View.Protected.class)
    private String password;

    @NotEmpty
    @JsonView(View.Public.class)
    private String city;

    @JsonView(View.Private.class)
    private String[] roles;

    public User(String emailAddress, String fullName, String password) {
        this.emailAddress = emailAddress;
        this.fullName = fullName;
        this.password = password;
    }

    public User() {

    }

    @Override
    @JsonIgnore
    public String getName() {
        return null;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public boolean hasRole(String roleName) {
        if (roles != null) {
            for (String role : roles) {
                return roleName.equals(role);
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(emailAddress, user.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress);
    }
}
