package org.beauty.entity;

public enum OrderStatus {

    REGISTERED(0),
    CANCELLED(-1),
    IN_WORK(1),
    IS_READY(2),
    IS_DELIVERED(3);



    private final int statusValue;

    OrderStatus(int statusValue) {
        this.statusValue = statusValue;
    }

    public int getStatusValue() {
        return statusValue;
    }
}
