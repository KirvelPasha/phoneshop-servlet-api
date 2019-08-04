package com.es.phoneshop.cart;

import com.es.phoneshop.exceptions.OutOfStockException;

import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);

    void add(Cart cart, Long productId, Long quantity) throws OutOfStockException;

    void update(Cart cart, Long productId, Long quantity) throws OutOfStockException;

    void delete(Cart cart, Long productId);
}
