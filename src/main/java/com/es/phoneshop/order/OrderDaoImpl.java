package com.es.phoneshop.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static OrderDaoImpl instance = new OrderDaoImpl();
    private List<Order> orders;
    private long id;

    private OrderDaoImpl() {
        orders = new ArrayList<>();
    }

    public static OrderDaoImpl getInstance() {
        return instance;
    }

    @Override
    public void save(Order order) {
        order.setId(++id);
        orders.add(order);
    }

    @Override
    public Optional<Order> getBySecureId(String secureId) {
        return orders
                .stream()
                .filter(order -> order.getSecureId().equals(secureId))
                .findAny();
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }
}
