package com.es.phoneshop.cart;


import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
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
    public Cart getCart(HttpServletRequest request) {
        Cart result = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if (result == null) {
            result = new Cart();
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public void add(Cart cart, Long productId, Long quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId).get();
        Optional<CartItem> optionalCartItem = cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProductId().equals(productId))
                .findFirst();
        Long currentQuantity = optionalCartItem
                .map(CartItem::getQuantity)
                .orElse(0L);
        if (currentQuantity + quantity > product.getStock() || quantity <= 0) {
            throw new OutOfStockException(product.getStock());
        } else if (currentQuantity == 0) {
            cart.getCartItems().add(new CartItem(product.getId(), quantity));
        } else {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(currentQuantity + quantity);
        }
        BigDecimal newTotalCost = cart.getTotalCost().add(product.getLastPrice().multiply(new BigDecimal(quantity)));
        cart.setTotalCost(newTotalCost);
        cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
    }
}
