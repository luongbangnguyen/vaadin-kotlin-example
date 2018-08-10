package com.vaadin.example.domain.util.pageable;

import java.util.List;

public class Pageable {
    private int page;
    private int size;
    private List<Order> orders;

    @Deprecated
    public Pageable() {
    }

    public Pageable(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public Pageable(int page, int size, List<Order> orders) {
        this.page = page;
        this.size = size;
        this.orders = orders;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
