package com.es.phoneshop.web;


import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.recentProducts.HttpSessionRecentViewService;
import com.es.phoneshop.recentProducts.RecentView;
import com.es.phoneshop.recentProducts.RecentViewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductDetailsPageServlet extends HttpServlet {
    protected static final String QUANTITY = "quantity";
    private ProductDao productDao;
    private CartService cartService;
    private RecentViewService recentViewService;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        recentViewService = HttpSessionRecentViewService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = parseId(request);
        Optional<Product> optionalProduct = productDao.getProduct(id);

        if (optionalProduct.isPresent()) {
            RecentView recentView = recentViewService.getRecentView(request.getSession());
            recentViewService.add(recentView, id);
            request.setAttribute("cart", cartService.getCart(request.getSession()));
            request.setAttribute("product", optionalProduct.get());
            request.setAttribute("recentView", recentView.getRecentlyViewed());
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } else {
            response.setStatus(404);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        Long productId = parseId(request);
        try {
            Long quantity = Long.valueOf(request.getParameter(QUANTITY));
            cartService.add(cart, productId, quantity);

            response.sendRedirect(request.getContextPath() + request.getServletPath() + request.getPathInfo()
                    + "?message=Added to cart successfully");

        } catch (NumberFormatException exception) {
            response.sendRedirect(request.getContextPath() + request.getServletPath() + request.getPathInfo()
                    + "?error=Not a number");
        } catch (OutOfStockException exception) {
            response.sendRedirect(request.getContextPath() + request.getServletPath() + request.getPathInfo()
                    + "?error=Out of stock. Max stock is " + exception.getMaxStock());
        }
    }

    private Long parseId(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
