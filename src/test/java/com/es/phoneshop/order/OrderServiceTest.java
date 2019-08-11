package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private Cart cart;
    @Mock
    private CartItem cartItem;

    private List<CartItem> cartItems = new ArrayList<>();
    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    public void createOrderTest() {
        cartItems.add(cartItem);
        when(cart.getCartItems()).thenReturn(cartItems);
        when(cart.getTotalCost()).thenReturn(new BigDecimal(50));

        Order order = orderService.createOrder(cart);


        assertEquals(cartItems, order.getCartItems());
        assertEquals(new BigDecimal(55), order.getTotalCost());
    }


    @Test
    public void placeOrder() {

    }
}
