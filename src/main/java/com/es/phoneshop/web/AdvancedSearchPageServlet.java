package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Stream;

public class AdvancedSearchPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description") != null ? request.getParameter("description") : " ";
        String minPrice = request.getParameter("minPrice") != null ? request.getParameter("minPrice") : " ";
        String maxPrice = request.getParameter("minPrice") != null ? request.getParameter("maxPrice") : " ";
        String minStock = request.getParameter("minStock") != null ? request.getParameter("minStock") : " ";
        String maxStock = request.getParameter("maxStock") != null ? request.getParameter("maxStock") : " ";
        boolean allEmpty = Stream
                .of(description,minPrice,maxPrice,minStock,maxStock)
                .allMatch(String::isEmpty);
        if (allEmpty) {
            request.setAttribute("products",productDao.findProducts());
            request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
        }
        int intMinPrice = 0;
        int intMaxPrice = 0;
        int intMinStock = 0;
        int intMaxStock = 0;
        try {
             intMinPrice = parse(request,"minPrice");
             intMaxPrice = parse(request,"maxPrice");
             intMinStock = parse(request,"minStock");
             intMaxStock = parse(request,"maxStock");
             List<Product> productList = productDao.search(description,intMinPrice,intMaxPrice,intMinStock,intMaxStock);
             request.setAttribute("products",productList);
             request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
        } catch (NumberFormatException errors) {
            request.setAttribute("products",productDao.findProducts());
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
        } catch (ParseException e) {
            request.setAttribute("errors", e.toString());
        }
    }


    private Integer parsParams(String param) {
        try {
            return Integer.valueOf(param);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Not a number");
        }
    }

    private int parse(HttpServletRequest request,String param) throws ParseException {
        String value = request.getParameter(param);
        if (value == null || value.equals("")) {
            return Integer.MIN_VALUE;
        }
        return NumberFormat.getInstance().parse(value).intValue();
    }
}
