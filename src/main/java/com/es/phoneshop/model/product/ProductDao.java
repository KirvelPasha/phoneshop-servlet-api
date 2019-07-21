package com.es.phoneshop.model.product;

import java.util.List;
import java.util.Optional;


public interface ProductDao {
    Optional<Product> getProduct(Long id);
    List<Product> findProducts();
    void save(Product product);
    void delete(Long id);
    List<Product> findProducts(String query);
    List<Product> findProducts(String query, String sortBy, String order);
}
