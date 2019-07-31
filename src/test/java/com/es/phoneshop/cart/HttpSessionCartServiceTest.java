package com.es.phoneshop.cart;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.es.phoneshop.cart.HttpSessionCartService.CART_SESSION_ATTRIBUTE;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private List<CartItem> cartItems;
    @Mock
    private Product product;
    @Mock
    private Cart cart;
    @Mock
    private ArrayListProductDao productDao;
    @Mock
    private CartItem cartItem1;
    @Mock
    private CartItem cartItem2;
    @Mock
    private BigDecimal totalCost;
    @InjectMocks
    private HttpSessionCartService httpSessionCartService;

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CART_SESSION_ATTRIBUTE)).thenReturn(cart);

        when(product.getStock()).thenReturn(100);
        when(product.getLastPrice()).thenReturn(new BigDecimal(50));


        when(cart.getTotalCost()).thenReturn(totalCost);
        when(cart.getCartItems()).thenReturn(cartItems);

        setupCartItem(cartItem1, 2L, 10L);
        setupCartItem(cartItem2, 3L, 11L);

        cartItems = Stream.of(cartItem1, cartItem2).collect(Collectors.toList());

        when(cart.getCartItems()).thenReturn(cartItems);

        setupCartItem(cartItem1, 2L, 10L);
        setupCartItem(cartItem2, 3L, 11L);

    }

    private void setupCartItem(CartItem cartItem, Long productId, Long quantity) {
        when(cartItem.getProductId()).thenReturn(productId);
        when(cartItem.getQuantity()).thenReturn(quantity);
    }


    @Test
    public void getCartTest() {
        Cart existingCart = new Cart();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CART_SESSION_ATTRIBUTE)).thenReturn(existingCart);
        Cart cart = httpSessionCartService.getCart(request);

        assertEquals(existingCart, cart);
    }

    @Test
    public void getCartTest2() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CART_SESSION_ATTRIBUTE)).thenReturn(null);

        httpSessionCartService.getCart(request);

        verify(request, times(2)).getSession();
        verify(session).setAttribute(eq(CART_SESSION_ATTRIBUTE), eq(new Cart()));
    }

    @Test
    public void addTest() throws OutOfStockException {
        when(productDao.getProduct(2L)).thenReturn(Optional.of(product));
        Long quantity = 12L;
        httpSessionCartService.add(cart, 2L, quantity);
        Long newQuantity = cartItem1.getQuantity() + quantity;

        when(cartItem1.getQuantity()).thenReturn(newQuantity);
        assertEquals(newQuantity, cart.getCartItems().get(0).getQuantity());
    }

    @Test
    public void addCartItemIsPresentTest() throws OutOfStockException {
        when(productDao.getProduct(1L)).thenReturn(Optional.of(product));
        httpSessionCartService.add(cart, 1L, 12L);

        assertEquals(3, cart.getCartItems().size());
    }

    @Test(expected = OutOfStockException.class)
    public void testAddWithNegativeQuantity() throws OutOfStockException {
        when(productDao.getProduct(2L)).thenReturn(Optional.of(product));

        httpSessionCartService.add(cart, 2L, -5L);
    }
}
