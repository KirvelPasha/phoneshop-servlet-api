package com.es.phoneshop.web;

import com.es.phoneshop.enums.SortBy;
import com.es.phoneshop.enums.SortingOrder;
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
import java.util.List;

import static com.es.phoneshop.web.ProductListPageServlet.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;

    private List<Product> productList;
    @Mock
    private RecentViewService recentViewService;
    @Mock
    private RecentView recentView;

    private Deque<Product> recentlyViewed;
    @InjectMocks
    private ProductListPageServlet servlet;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter(SORTBY)).thenReturn(SortBy.DESCRIPTION.getSortBy());
        when(request.getParameter(ORDER)).thenReturn(SortingOrder.ASC.getSorting());
        when(recentViewService.getRecentView(request)).thenReturn(recentView);
        when(recentView.getRecentlyViewed()).thenReturn(recentlyViewed);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(productDao.findProducts("1", SortBy.DESCRIPTION, SortingOrder.ASC)).thenReturn(productList);
        when(request.getParameter(QUERY)).thenReturn("1");
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("products"), eq(productList));
        verify(request).setAttribute(eq("recentView"), eq(recentlyViewed));
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/pages/productList.jsp");
        verify(requestDispatcher).forward(request, response);

    }

    @Test
    public void testDoGetWithEmtyQuery() throws ServletException, IOException {
        when(productDao.findProducts(" ", SortBy.DESCRIPTION, SortingOrder.ASC)).thenReturn(productList);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("products"), eq(productList));
        verify(request).setAttribute(eq("recentView"), eq(recentlyViewed));
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/pages/productList.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}