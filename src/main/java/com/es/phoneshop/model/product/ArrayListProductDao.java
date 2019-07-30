package com.es.phoneshop.model.product;

import com.es.phoneshop.enums.SortBy;
import com.es.phoneshop.enums.SortingOrder;

import java.util.*;
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
                .filter(product -> product.getStock() > 0 && product.getLastPrice() != null)
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

        String[] searchWords = query
                .trim()
                .replaceAll("\\s{2,}", " ")
                .toLowerCase()
                .split(" ");

        return ArrayListProductDao.getInstance().findProducts().stream()
                .collect(Collectors.toMap(product -> product, product -> Arrays.stream(searchWords)
                        .filter(word -> product.getDescription().toLowerCase().contains(word)).count()))
                .entrySet().stream()
                .filter(map -> map.getValue() > 0)
                .sorted((x, y) -> y.getValue().compareTo(x.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findProducts(String query, SortBy sortBy, SortingOrder order) {
        List<Product> productList = findProducts(query);
        if (sortBy == null) {
            return productList;
        }
        Comparator<Product> productComparator = sortBy.equals(SortBy.DESCRIPTION)
                ? Comparator.comparing(Product::getDescription, Comparator.comparing(String::toLowerCase))
                : sortBy.equals(SortBy.PRICE)
                ? Comparator.comparing(Product::getLastPrice)
                : null;
        if (productComparator == null) {
            throw new IllegalArgumentException("Incorrect sortBy");
        }
        if (order.equals(SortingOrder.DESC)) {
            productComparator = productComparator.reversed();
        }
        productList.sort(productComparator);
        return productList;
    }

    protected void setProducts(List<Product> productList) {
        this.productList = productList;
    }
}
