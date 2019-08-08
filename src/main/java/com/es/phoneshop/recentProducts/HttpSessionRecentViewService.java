package com.es.phoneshop.recentProducts;

import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.Deque;
import java.util.Optional;

public class HttpSessionRecentViewService implements RecentViewService {
    protected static final String VIEW_SESSION_ATTRIBUTE = HttpSessionCartService.class + ".view";
    private static HttpSessionRecentViewService recentViewService = new HttpSessionRecentViewService();
    private ArrayListProductDao productDao;

    public HttpSessionRecentViewService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static HttpSessionRecentViewService getInstance() {
        return recentViewService;
    }

    @Override
    public RecentView getRecentView(HttpSession session) {
        RecentView result = (RecentView) session.getAttribute(VIEW_SESSION_ATTRIBUTE);
        if (result == null) {
            result = new RecentView();
            session.setAttribute(VIEW_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public void add(RecentView recentViews, Long productId) {
        Product product = productDao.getProduct(productId).get();
        Deque<Product> productQueue = recentViews.getRecentlyViewed();
        Optional<Product> optionalProduct = productQueue
                .stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();
        if (!optionalProduct.isPresent()) {
            productQueue.addFirst(product);
        }
        if (productQueue.size() > 3) {
            productQueue.removeLast();
        }
    }
}
