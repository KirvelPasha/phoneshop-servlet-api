package com.es.phoneshop.cart;

import com.es.phoneshop.exceptions.OutOfStockException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);

    void add(Cart cart, Long productId, Long quantity) throws OutOfStockException;
}
