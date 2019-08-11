package com.es.phoneshop.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    void save(Order order);

    Optional<Order> getBySecureId(String secureId);

    List<Order> getOrders();
}
