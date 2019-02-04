package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.auth.Auth;
import nl.hsleiden.View;
import nl.hsleiden.model.User;
import nl.hsleiden.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@Singleton
@Path("/users")
public class UserResource {

    private final UserService service;

    @Inject
    public UserResource(UserService service) {
        this.service = service;
    }

    @GET
    @RolesAllowed("admin")
    public Collection<User> getAll(@Auth User user) {
        return service.getAll();
    }

    @GET
    @Path("/{email}")
    @JsonView(View.Public.class)
    public User retrieve(@PathParam("email") String email) {
        return service.get(email);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Public.class)
    public User create(@Valid User user) {
        return service.add(user);
    }

    @PUT
    @Path("/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void update(@PathParam("email") String email, @Auth User authenticator, User user) {
        service.update(authenticator, email, user);
    }

    @DELETE
    @Path("/{email}")
    @RolesAllowed("admin")
    @JsonView(View.Protected.class)
    public void delete(@Auth User user, @PathParam("email") String email) {
        System.out.println("Wanting to delete: " + email);
        if (!user.getEmail().equals(email)) {
            System.out.println("Delete: " + email);
            service.delete(email);
        }
    }

    @GET
    @Path("/me")
    @JsonView(View.Private.class)
    public User authenticate(@Auth User authenticator) {
        return authenticator;
    }
}
