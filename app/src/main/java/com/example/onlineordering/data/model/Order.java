package com.example.onlineordering.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private Date orderDate;
    private ArrayList<Product> orderProducts;
    private Date orderDeadline;
    private Warehouse warehouse;

    public Order(String orderId, Date orderDate, ArrayList<Product> orderProducts, Date orderDeadline, Warehouse warehouse) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderProducts = orderProducts;
        this.orderDeadline = orderDeadline;
        this.warehouse = warehouse;
    }

    public String getOrderId() {
        return orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public List<Product> getOrderProducts() {
        return orderProducts;
    }

    public Date getOrderDeadline() {
        return orderDeadline;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
}
