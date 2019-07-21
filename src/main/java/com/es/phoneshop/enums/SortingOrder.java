package com.es.phoneshop.enums;

public enum SortingOrder {
    ASC("asc"),
    DESC("desc");

    private String sorting;

    SortingOrder(String sorting) {
        this.sorting = sorting;
    }

    public String getSorting() {
        return sorting;
    }
}
