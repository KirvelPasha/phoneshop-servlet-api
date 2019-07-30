package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductDetailsPageServlet extends HttpServlet {
    private static final String QUANTITY = "quantity";
    private static final String ERROR = "error";
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = parseId(request);
        Optional<Product> optionalProduct = productDao.getProduct(id);

        if (optionalProduct.isPresent()) {
            request.setAttribute("product", optionalProduct.get());
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } else {
            response.setStatus(404);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }

    private Long parseId(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
