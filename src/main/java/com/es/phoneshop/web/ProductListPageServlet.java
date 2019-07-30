package com.es.phoneshop.web;

import com.es.phoneshop.enums.SortBy;
import com.es.phoneshop.enums.SortingOrder;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    protected static final String QUERY = "query";
    protected static final String SORTBY = "sortBy";
    protected static final String ORDER = "order";
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(QUERY);
        SortBy sortBy = request.getParameter(SORTBY) != null ? SortBy.valueOf(request.getParameter(SORTBY)) : null;
        SortingOrder order = request.getParameter(ORDER) != null ? SortingOrder.valueOf(request.getParameter(ORDER)) : SortingOrder.ASC;

        if (query == null) {
            request.setAttribute("products", productDao.findProducts(" ", sortBy, order));
        } else {
            request.setAttribute("products", productDao.findProducts(query, sortBy, order));
        }
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
