package nl.hsleiden.service;


import nl.hsleiden.model.Product;
import nl.hsleiden.persistence.ProductDAO;

import javax.inject.Inject;
import java.io.*;
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

    public void add(Product product) {
        dao.addProduct(product);
    }

    public void writeToFile(InputStream uploadImage, String location) {
        try {
            OutputStream out1;
            int read = 0;
            byte[] bytes = new byte[1024];

            out1 = new FileOutputStream(new File(location));
            while ((read = uploadImage.read(bytes)) != -1) {
                out1.write(bytes, 0, read);
            }
            out1.flush();
            out1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String name) {
        dao.deleteProduct(name);
    }

    public void update(Product product, String name) {
        dao.updateProduct(product, name);
    }
}
