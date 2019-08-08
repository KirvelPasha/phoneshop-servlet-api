package com.es.phoneshop.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart implements Serializable {
    private List<CartItem> cartItems = new ArrayList<>();
    private BigDecimal totalCost = new BigDecimal(0);
    private Long totalQuantity = 0L;


    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                " totalCost=" + totalCost +
                ", totalQuantity=" + totalQuantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(cartItems, cart.cartItems) &&
                Objects.equals(totalCost, cart.totalCost) &&
                Objects.equals(totalQuantity, cart.totalQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItems, totalCost, totalQuantity);
    }
}
