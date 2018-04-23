package com.vaadin.example.domain.util.pageable;

public class Order {
    private String name;
    private Direction direction;

    public Order(String name, Direction direction) {
        this.name = name;
        this.direction = direction;
    }

    public Order() {
    }

    public String getName() {
        return name;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
