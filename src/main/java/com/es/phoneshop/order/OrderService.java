package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;

public interface OrderService {
    Order createOrder(Cart cart);

    void placeOrder(Order order);
}
