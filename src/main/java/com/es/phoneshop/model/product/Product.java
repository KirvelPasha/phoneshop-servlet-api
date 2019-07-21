package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedList;
import java.util.Objects;

public class Product {
    private Long id;
    private String code;
    private String description;
    /* null means there is no price because the product is outdated or new */
    /* can be null if the price is null */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private LinkedList<PriceHistory> priceHistories;
    private BigDecimal lastPrice;

    public Product() {
    }

    public Product(Long id, String code, String description, Currency currency, int stock, String imageUrl, LinkedList<PriceHistory> priceHistories, BigDecimal lastPrice) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistories = priceHistories;
        this.lastPrice = lastPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LinkedList<PriceHistory> getPriceHistories() {
        return priceHistories;
    }

    public void setPriceHistories(LinkedList<PriceHistory> priceHistories) {
        this.priceHistories = priceHistories;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return stock == product.stock &&
                Objects.equals(id, product.id) &&
                Objects.equals(code, product.code) &&
                Objects.equals(description, product.description) &&
                Objects.equals(currency, product.currency) &&
                Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, description, currency, stock, imageUrl);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", currency=" + currency +
                ", stock=" + stock +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}