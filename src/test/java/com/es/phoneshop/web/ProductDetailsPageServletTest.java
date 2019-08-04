package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
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
import java.io.IOException;
import java.util.Deque;
import java.util.Optional;

import static com.es.phoneshop.web.ProductDetailsPageServlet.QUANTITY;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private Product product;
    @Mock
    private RecentView recentView;

    private Deque<Product> recentlyViewed;
    @Mock
    private RecentViewService recentViewService;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @InjectMocks
    private ProductDetailsPageServlet servlet;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getContextPath()).thenReturn("/phoneshop-servlet-api");
        when(request.getServletPath()).thenReturn("/products");
        when(request.getPathInfo()).thenReturn("/1");
        when(recentViewService.getRecentView(request.getSession())).thenReturn(recentView);
        when(cartService.getCart(request.getSession())).thenReturn(cart);
        when(recentView.getRecentlyViewed()).thenReturn(recentlyViewed);
        when(request.getParameter(QUANTITY)).thenReturn("3");
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(productDao.getProduct(1L)).thenReturn(Optional.of(product));

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("cart"), eq(cart));
        verify(request).setAttribute(eq("product"), eq(product));
        verify(request).setAttribute(eq("recentView"), eq(recentlyViewed));
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/pages/product.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetProductNotFound() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostWithNumberFormatException() throws ServletException, IOException {
        when(request.getParameter(QUANTITY)).thenReturn("abc");
        String expect = "/phoneshop-servlet-api/products/1?error=Not a number";
        servlet.doPost(request, response);


        verify(response).sendRedirect(eq(expect));
    }

    @Test
    public void testDoPostWithOutOfStockException() throws ServletException, IOException, OutOfStockException {
        when(request.getParameter(QUANTITY)).thenReturn("1000");
        doThrow(new OutOfStockException(100)).when(cartService).add(cart, 1L, 1000L);
        String expect = "/phoneshop-servlet-api/products/1?error=Out of stock. Max stock is 100";

        servlet.doPost(request, response);

        verify(response).sendRedirect(eq(expect));
    }
}
