package com.es.phoneshop.enums;

public enum SortBy {
    DESCRIPTION("description"),
    PRICE("price");

    private String sortBy;

    SortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }

}
