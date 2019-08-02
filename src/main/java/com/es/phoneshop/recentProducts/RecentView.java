package com.es.phoneshop.recentProducts;

import com.es.phoneshop.model.product.Product;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

public class RecentView {
    private Deque<Product> recentlyViewed = new LinkedList<>();


    public Deque<Product> getRecentlyViewed() {
        return recentlyViewed;
    }

    @Override
    public String toString() {
        return "RecentView{" +
                "recentlyViewed=" + recentlyViewed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecentView that = (RecentView) o;
        return Objects.equals(recentlyViewed, that.recentlyViewed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recentlyViewed);
    }
}
