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

@Singleton
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService service;

    @Inject
    public UserResource(UserService service) {
        this.service = service;
    }

    @GET
    @Path("/{email}")
    @JsonView(View.Public.class)
    @RolesAllowed("GUEST")
    public User retrieve(@PathParam("email") String email) {
        return service.get(email);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void create(@Valid User user) {
        service.add(user);
    }

    @PUT
    @Path("/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    @RolesAllowed("GUEST")
    public void update(@PathParam("email") String email, @Auth User authenticator, User user) {
        service.update(authenticator, email, user);
    }

    @DELETE
    @Path("/{email}")
    @RolesAllowed("ADMIN")
    public void delete(@PathParam("email") String email) {
        service.delete(email);
    }

    @GET
    @Path("/me")
    @JsonView(View.Private.class)
    public User authenticate(@Auth User authenticator) {
        return authenticator;
    }
}
