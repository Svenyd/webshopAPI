package nl.hsleiden.resource;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import nl.hsleiden.model.Product;
import nl.hsleiden.service.ProductService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

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
    @JsonView(View.Public.class)
    public Collection<Product> getProducts() {
        return service.getAll();
    }
}
