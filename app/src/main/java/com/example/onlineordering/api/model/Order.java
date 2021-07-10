package com.example.onlineordering.api.model;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String _id;
    private LocalDate orderDate;
    private ArrayList<Product> orderProducts;
    private LocalDate orderDeadline;

    public Order(String orderId, LocalDate orderDate, ArrayList<Product> orderProducts, LocalDate orderDeadline) {
        this._id = orderId;
        this.orderDate = orderDate;
        this.orderProducts = orderProducts;
        this.orderDeadline = orderDeadline;
    }

    public String getOrderId() {
        return _id;
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

    @NonNull
    @Override
    public String toString() {
        return "ID: " + this._id + "          " + "Deadline: " + this.orderDeadline;
    }
}
