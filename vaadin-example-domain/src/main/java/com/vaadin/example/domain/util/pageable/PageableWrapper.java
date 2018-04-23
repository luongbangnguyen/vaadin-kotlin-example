package com.vaadin.example.domain.util.pageable;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PageableWrapper<T>{
    private T criteria;
    private Pageable pageable;

    @Deprecated
    public PageableWrapper() {
    }

    public PageableWrapper(T criteria, Pageable pageable) {
        this.criteria = criteria;
        this.pageable = pageable;
    }

    @Transient
    public static <T> PageableWrapper<T> covert(T criteria,org.springframework.data.domain.Pageable pageable) {
        Iterator<Sort.Order> orderList = pageable.getSort().iterator();
        List<Order> oderConcerted = new ArrayList<>();
        while (orderList.hasNext()) {
            Sort.Order order = orderList.next();
            if (order.getDirection().isAscending()) {
                oderConcerted.add(new Order(order.getProperty(), Direction.ASC));
            } else {
                oderConcerted.add(new Order(order.getProperty(), Direction.DESC));
            }
        }
        return new PageableWrapper<>(criteria, new Pageable(pageable.getPageNumber(), pageable.getPageSize(), oderConcerted));
    }

    @Transient
    public org.springframework.data.domain.Pageable toPageable() {
        if (this.pageable == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(this.pageable.getOrders()))
        {
            return PageRequest.of(this.pageable.getPage(), this.pageable.getSize());
        }

        return PageRequest.of(this.pageable.getPage(), this.pageable.getSize(), Sort.by(this.getOrders()));
    }

    @Transient
    private List<Sort.Order> getOrders() {
        return this.pageable.getOrders().stream().map(it -> {
            if (StringUtils.equals(it.getDirection().name(), Sort.Direction.DESC.name())) {
                return new Sort.Order(Sort.Direction.DESC, it.getName());
            } else {
                return new Sort.Order(Sort.Direction.ASC, it.getName());
            }
        }).collect(Collectors.toList());
    }

    public T getCriteria() {
        return criteria;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setCriteria(T criteria) {
        this.criteria = criteria;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
