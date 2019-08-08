package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.recentProducts.HttpSessionRecentViewService;
import com.es.phoneshop.recentProducts.RecentView;
import com.es.phoneshop.recentProducts.RecentViewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;
    private RecentViewService recentViewService;

    @Override
    public void init() throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        recentViewService = HttpSessionRecentViewService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecentView recentView = recentViewService.getRecentView(request.getSession());
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.setAttribute("recentView", recentView.getRecentlyViewed());
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        String[] errors = new String[productIds.length];
        Cart cart = cartService.getCart(request.getSession());
        for (int i = 0; i < productIds.length; i++) {
            Long productId = Long.valueOf(productIds[i]);
            Long quantity = parsQuantity(quantities, errors, i);
            if (quantity != null) {
                try {
                    cartService.update(cart, productId, quantity);
                } catch (OutOfStockException ex) {
                    errors[i] = "Out of stock. Max stock is " + ex.getMaxStock();
                }
            }
        }
        boolean hasError = Arrays.stream(errors).anyMatch(Objects::nonNull);
        if (hasError) {
            request.setAttribute("errors", errors);
            doGet(request, response);
        } else {
            response.sendRedirect(request.getRequestURI() + "?message=Update successfully");
        }
    }


    private Long parsQuantity(String[] quantities, String[] errors, int index) {
        try {
            return Long.valueOf(quantities[index]);
        } catch (NumberFormatException ex) {
            errors[index] = "Not a number";
        }
        return null;
    }
}
