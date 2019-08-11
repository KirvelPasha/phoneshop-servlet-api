package com.es.phoneshop.web;


import com.es.phoneshop.order.Order;
import com.es.phoneshop.order.OrderDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private OrderDao orderDao;
    @Mock
    private Order order;
    @InjectMocks
    private OrderOverviewPageServlet orderOverviewPageServlet;

    private String secureId = "/test";

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn(secureId);
        secureId = secureId.substring(1);
        when(orderDao.getBySecureId(secureId)).thenReturn(Optional.of(order));
    }

    @Test
    public void doGet() throws ServletException, IOException {
        orderOverviewPageServlet.doGet(request, response);

        verify(request).setAttribute(eq("order"), eq(order));
        verify(requestDispatcher).forward(request, response);
    }
}
