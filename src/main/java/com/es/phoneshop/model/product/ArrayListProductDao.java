package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static ArrayListProductDao instance = new ArrayListProductDao();
    private Long counter = 0L;
    private List<Product> productList;


    private ArrayListProductDao() {
        this.productList = new ArrayList<>();
    }

    public static ArrayListProductDao getInstance() {
        return instance;
    }

    @Override
    public synchronized Optional<Product> getProduct(Long id) {
        return productList
                .stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public synchronized List<Product> findProducts() {
        return productList
                .stream()
                .filter(product -> product.getStock() > 0 && product.getPriceHistories().getLast() != null)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
      if (product.getId() != null) {
          throw new IllegalArgumentException("You can't set id");
      }
      product.setId(++counter);
      productList.add(product);
    }

    @Override
    public synchronized void delete(Long id) {
        productList.removeIf(product -> product.getId().equals(id));
    }

    @Override
    public synchronized List<Product> findProducts(String query) {
        if (query == null || query.trim().length() == 0) {
            return findProducts();
        }
        return findProducts()
                .stream()
                .filter(product -> product.getDescription().contains(query))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findProducts(String query, String sortBy, String order) {
        List<Product> productList = findProducts(query);
        if (sortBy == null) {
            return productList;
        }
        Comparator<Product> productComparator = null;
        switch (sortBy) {
            case "price":
                productComparator = Comparator.comparing(Product::getLastPrice);
                break;
            case "description":
                productComparator = Comparator.comparing(Product::getDescription,Comparator.comparing(String::toLowerCase));
                break;
        }
        if (productComparator == null){
            return productList;
        }
        if (order.equals("desc")) {
            productComparator = productComparator.reversed();
        }
        productList.sort(productComparator);
        return productList;
    }

}
