package com.es.phoneshop.model.product;

import com.es.phoneshop.enums.SortBy;
import com.es.phoneshop.enums.SortingOrder;

import java.util.List;
import java.util.Optional;


public interface ProductDao {
    Optional<Product> getProduct(Long id);

    List<Product> findProducts();

    void save(Product product);

    void delete(Long id);

    List<Product> findProducts(String query);

    List<Product> findProducts(String query, SortBy sortBy, SortingOrder order);
}
