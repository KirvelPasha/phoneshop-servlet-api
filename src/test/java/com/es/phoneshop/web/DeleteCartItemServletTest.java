package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCartItemServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @InjectMocks
    private DeleteCartItemServlet deleteCartItemServlet;

    @Before
    public void setup() {
        when(cartService.getCart(request.getSession())).thenReturn(cart);
        when(request.getPathInfo()).thenReturn("/1");
    }

    @Test
    public void doPost() throws ServletException, IOException {
        deleteCartItemServlet.doPost(request, response);

        verify(cartService).delete(eq(cart), eq(1L));
    }
}
