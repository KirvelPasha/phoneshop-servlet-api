package com.es.phoneshop.web;


import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.order.Order;
import com.es.phoneshop.order.OrderService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private CartService cartService;
    @Mock
    private OrderService orderService;
    @Mock
    private Cart cart;
    @Mock
    private Order order;
    @InjectMocks
    private CheckoutPageServlet checkoutPageServlet;

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(cartService.getCart(session)).thenReturn(cart);
        when(orderService.createOrder(cart)).thenReturn(order);
        when(order.getSecureId()).thenReturn("test");
        when(request.getParameter("paymentMethod")).thenReturn("cash");
        when(request.getParameter("deliveryMethod")).thenReturn("courier");

    }

    @Test
    public void doGet() throws ServletException, IOException {
        checkoutPageServlet.doGet(request, response);

        verify(request).setAttribute(eq("order"), eq(order));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("pasha");
        when(request.getParameter("phone")).thenReturn("+375295675566");
        when(request.getParameter("surname")).thenReturn("kirvel");
        when(request.getContextPath()).thenReturn("/phoneshop-servlet-api");
        when(request.getParameter("deliveryAddress")).thenReturn("address");

        checkoutPageServlet.doPost(request, response);

        verify(response).sendRedirect(eq("/phoneshop-servlet-api/order/overview/test"));
    }

    @Test
    public void doPostWithWrongData() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("phone")).thenReturn("375295675566");
        when(request.getParameter("surname")).thenReturn("kirvel");
        when(request.getParameter("deliveryAddress")).thenReturn("address");

        checkoutPageServlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), eq("Wrong data"));
    }
}
