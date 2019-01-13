package nl.hsleiden.service;


import nl.hsleiden.model.Product;
import nl.hsleiden.persistence.ProductDAO;

import javax.inject.Inject;
import java.util.Collection;

public class ProductService {

    private ProductDAO dao;

    @Inject
    public ProductService(ProductDAO dao) {
        this.dao = dao;
    }

    public Collection<Product> getAll() {
        return this.dao.getAllProducts();
    }
}
