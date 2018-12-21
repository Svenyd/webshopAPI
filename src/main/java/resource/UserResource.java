package resource;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.auth.Auth;
import model.User;
import service.UserService;

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
    @Path("/{id}")
    @JsonView(View.Public.class)
    @RolesAllowed("GUEST")
    public User retrieve(@PathParam("id") int id) {
        return service.get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void create(@Valid User user) {
        service.add(user);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    @RolesAllowed("GUEST")
    public void update(@PathParam("id") int id, @Auth User authenticator, User user) {
        service.update(authenticator, id, user);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public void delete(@PathParam("id") int id) {
        service.delete(id);
    }

    @GET
    @Path("/me")
    @JsonView(View.Private.class)
    public User authenticate(@Auth User authenticator) {
        return authenticator;
    }
}
