package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.recentProducts.RecentView;
import com.es.phoneshop.recentProducts.RecentViewService;
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
import java.util.Deque;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
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
    private RecentViewService recentViewService;
    @Mock
    private RecentView recentView;
    @Mock
    private Cart cart;

    private Deque<Product> recentlyViewed;

    private String[] productIds = new String[]{"1", "2", "3"};
    private String[] quantities = new String[]{"2", "3", "4"};
    private String[] errors = new String[productIds.length];
    @InjectMocks
    private CartPageServlet cartPageServlet;

    @Before
    public void setup() {
        when(recentViewService.getRecentView(session)).thenReturn(recentView);
        when(recentView.getRecentlyViewed()).thenReturn(recentlyViewed);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(cartService.getCart(session)).thenReturn(cart);


        cartPageServlet.doGet(request, response);

        verify(request).setAttribute(eq("cart"), eq(cart));
        verify(request).setAttribute(eq("recentView"), eq(recentlyViewed));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getParameterValues("productId")).thenReturn(productIds);
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        when(request.getSession()).thenReturn(session);
        when(cartService.getCart(session)).thenReturn(cart);
        when(request.getRequestURI()).thenReturn("/phoneshop-servlet-api/cart");

        cartPageServlet.doPost(request, response);

        verify(response).sendRedirect(eq("/phoneshop-servlet-api/cart?message=Update successfully"));
    }


    @Test
    public void testDoPost2() throws ServletException, IOException, OutOfStockException {
        quantities = new String[]{"a", "3", "4"};
        errors = new String[]{"Not a number", null, "Out of stock. Max stock is 3"};
        when(request.getParameterValues("productId")).thenReturn(productIds);
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        when(request.getSession()).thenReturn(session);
        when(cartService.getCart(session)).thenReturn(cart);
        doThrow(new OutOfStockException(3)).when(cartService).update(cart, 3L, 4L);

        cartPageServlet.doPost(request, response);

        verify(request).setAttribute(eq("errors"), eq(errors));
    }
}
