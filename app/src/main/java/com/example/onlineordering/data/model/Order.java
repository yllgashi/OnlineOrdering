package com.example.onlineordering.data.model;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private LocalDate orderDate;
    private ArrayList<Product> orderProducts;
    private LocalDate orderDeadline;
    private Warehouse warehouse;

    public Order(String orderId, LocalDate orderDate, ArrayList<Product> orderProducts, LocalDate orderDeadline, Warehouse warehouse) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderProducts = orderProducts;
        this.orderDeadline = orderDeadline;
        this.warehouse = warehouse;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public List<Product> getOrderProducts() {
        return orderProducts;
    }

    public LocalDate getOrderDeadline() {
        return orderDeadline;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    @NonNull
    @Override
    public String toString() {
        return "ID: " + this.orderId + "          " + "Deadline: " + this.orderDeadline;
    }
}
