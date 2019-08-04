package com.es.phoneshop.recentProducts;

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
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

import static com.es.phoneshop.recentProducts.HttpSessionRecentViewService.VIEW_SESSION_ATTRIBUTE;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionRecentViewServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private ArrayListProductDao productDao;
    @Mock
    private RecentView recentViews;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;

    private Deque<Product> productQueue = new LinkedList<>();

    @InjectMocks
    private HttpSessionRecentViewService httpSessionRecentViewService;

    @Before
    public void setup() {
        setupProduct(product1, 1L);
        setupProduct(product2, 2L);
        setupProduct(product3, 3L);
        setupProduct(product4, 4L);

        productQueue.addFirst(product1);
        productQueue.addFirst(product2);

        when(recentViews.getRecentlyViewed()).thenReturn(productQueue);
    }

    private void setupProduct(Product product, Long id) {
        when(product.getId()).thenReturn(id);
    }

    @Test
    public void addProductIsPresentTest() {
        when(productDao.getProduct(1L)).thenReturn(Optional.of(product1));
        httpSessionRecentViewService.add(recentViews, 1L);
        assertSame(product1, productQueue.getLast());
        assertSame(product2, productQueue.getFirst());
    }

    @Test
    public void addTest() {
        when(productDao.getProduct(3L)).thenReturn(Optional.of(product3));
        httpSessionRecentViewService.add(recentViews, 3L);
        assertSame(product1, productQueue.getLast());
        assertSame(product3, productQueue.getFirst());
    }

    @Test
    public void addWhenSizeMoreThreeTest() {
        when(productDao.getProduct(4L)).thenReturn(Optional.of(product4));
        productQueue.addFirst(product3);
        httpSessionRecentViewService.add(recentViews, 4L);
        assertSame(product2, productQueue.getLast());
        assertSame(product4, productQueue.getFirst());
    }

    @Test
    public void getRecentViewTest() {
        RecentView existingResult = new RecentView();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(VIEW_SESSION_ATTRIBUTE)).thenReturn(existingResult);
        RecentView result = httpSessionRecentViewService.getRecentView(request.getSession());
        assertEquals(existingResult, result);
    }

    @Test
    public void getRecentViewTest2() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(VIEW_SESSION_ATTRIBUTE)).thenReturn(null);

        httpSessionRecentViewService.getRecentView(request.getSession());

        verify(session).setAttribute(eq(VIEW_SESSION_ATTRIBUTE), eq(new RecentView()));
    }
}
