package com.es.phoneshop.cart;


import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    protected static final String CART_SESSION_ATTRIBUTE = HttpSessionCartService.class + ".cart";
    private static HttpSessionCartService httpSessionCartService = new HttpSessionCartService();
    private ArrayListProductDao productDao;

    private HttpSessionCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static HttpSessionCartService getInstance() {
        return httpSessionCartService;
    }

    @Override
    public Cart getCart(HttpSession session) {
        Cart result = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        if (result == null) {
            result = new Cart();
            session.setAttribute(CART_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public void add(Cart cart, Long productId, Long quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId).get();
        Optional<CartItem> currentCartItem = getCurrentCartItemOptional(cart, productId);
        Long currentQuantity = getCurrentQuantity(currentCartItem);
        if (currentQuantity + quantity > product.getStock() || quantity <= 0) {
            throw new OutOfStockException(product.getStock());
        } else if (currentQuantity == 0) {
            cart.getCartItems().add(new CartItem(product, quantity));
        } else {
            CartItem cartItem = currentCartItem.get();
            cartItem.setQuantity(currentQuantity + quantity);
        }
        recalculateCart(cart);
    }

    @Override
    public void update(Cart cart, Long productId, Long quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId).get();
        if (product.getStock() < quantity || quantity <= 0) {
            throw new OutOfStockException(product.getStock());
        }
        Optional<CartItem> currentCartItem = getCurrentCartItemOptional(cart, productId);
        currentCartItem.ifPresent(cartItem -> cartItem.setQuantity(quantity));
        recalculateCart(cart);
    }

    @Override
    public void delete(Cart cart, Long productId) {
        CartItem itemToRemove = cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .get();
        cart.getCartItems().remove(itemToRemove);
        recalculateCart(cart);
    }


    private Optional<CartItem> getCurrentCartItemOptional(Cart cart, Long productId) {
        return cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findAny();
    }

    private Long getCurrentQuantity(Optional<CartItem> currentCartItem) {
        return currentCartItem
                .map(CartItem::getQuantity)
                .orElse(0L);
    }


    private void recalculateCart(Cart cart) {
        BigDecimal totalCost = new BigDecimal(
                cart.getCartItems()
                        .stream()
                        .mapToLong(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getLastPrice().longValue()).sum()
        );
        Long totalQuantity = cart.getCartItems()
                .stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
        cart.setTotalCost(totalCost);
        cart.setTotalQuantity(totalQuantity);
    }
}
