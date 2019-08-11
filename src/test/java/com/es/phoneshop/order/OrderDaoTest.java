package com.es.phoneshop.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDaoTest {

    @Mock
    private Order order;

    @InjectMocks
    private OrderDaoImpl orderDao;


    @Test
    public void save() {
        orderDao.save(order);

        assertSame(order, orderDao.getOrders().get(0));
    }

    @Test
    public void getBySecureId() {
        String secureId = "test";
        when(order.getSecureId()).thenReturn(secureId);
        orderDao.getOrders().add(order);

        Optional<Order> optionalOrder = orderDao.getBySecureId(secureId);

        assertSame(optionalOrder.get(), order);
    }
}
