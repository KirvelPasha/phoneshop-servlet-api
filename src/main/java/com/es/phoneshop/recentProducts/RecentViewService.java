package com.es.phoneshop.recentProducts;

import javax.servlet.http.HttpSession;

public interface RecentViewService {
    RecentView getRecentView(HttpSession session);

    void add(RecentView recentViews, Long productId);
}
