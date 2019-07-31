package com.es.phoneshop.recentProducts;

import javax.servlet.http.HttpServletRequest;

public interface RecentViewService {
    RecentView getRecentView(HttpServletRequest request);

    void add(RecentView recentViews, Long productId);
}
