package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.cart.HttpSessionCartService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl instance = new OrderServiceImpl();

    private OrderServiceImpl() {

    }

    public static OrderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setCartItems(new ArrayList<>(cart.getCartItems()));
        order.setSubTotalPrice(cart.getTotalCost());
        order.setDeliveryCost(new BigDecimal(5));
        order.setTotalCost(order.getSubTotalPrice().add(order.getDeliveryCost()));
        order.setDeliveryDate(new Date());

        return order;
    }

    @Override
    public void placeOrder(Order order) {
        order.setSecureId(UUID.randomUUID().toString());
        OrderDaoImpl.getInstance().save(order);
    }

//    private void recalculateQuantity(List<CartItem> cartItems) {
//       for (CartItem cartItem : cartItems) {
//
//       }
//
//    }
}
