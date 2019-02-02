package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import io.dropwizard.auth.Auth;
import nl.hsleiden.View;
import nl.hsleiden.model.Product;
import nl.hsleiden.model.User;
import nl.hsleiden.service.ProductService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@Singleton
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final ProductService service;

    @Inject
    public ProductResource(ProductService service) {
        this.service = service;
    }

    @GET
    public Collection<Product> getProducts() {
        return service.getAll();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed("admin")
    public void add(@Auth User authenticator,
                       @FormDataParam("name") String name,
                       @FormDataParam("price") double price,
                       @FormDataParam("ingredients") String jsonIngredients,
                       @FormDataParam("picture") InputStream uploadImage,
                       @FormDataParam("picture")FormDataContentDisposition fileDetail) {

        Gson g = new Gson();
        List<String> ingredients = g.fromJson(jsonIngredients, List.class);

        Product product = new Product(name, price, ingredients, fileDetail.getFileName());
        service.add(product);

        String url = "pictures/";
        if (fileDetail.getFileName() != null) {
            String location = url + fileDetail.getFileName();
            service.writeToFile(uploadImage, location);
        }
    }

    @DELETE
    @Path("/{name}")
    @RolesAllowed("admin")
    public void delete(@Auth User authenticator,
                       @PathParam("name") String name) {
        this.service.delete(name);
    }
}
