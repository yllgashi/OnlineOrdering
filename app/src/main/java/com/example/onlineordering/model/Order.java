package com.example.onlineordering.model;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String _id;
    private String orderDate;
    private ArrayList<Product> orderProducts;
    private String orderDeadline;

    public Order(String orderId, String orderDate, ArrayList<Product> orderProducts, String orderDeadline) {
        this._id = orderId;
        this.orderDate = orderDate;
        this.orderProducts = orderProducts;
        this.orderDeadline = orderDeadline;
    }

    public Order(String orderDate, ArrayList<Product> orderProducts, String orderDeadline) {
        this.orderDate = orderDate;
        this.orderProducts = orderProducts;
        this.orderDeadline = orderDeadline;
    }

    public String getOrderId() {
        return _id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public List<Product> getOrderProducts() {
        return orderProducts;
    }

    public String getOrderDeadline() {
        return orderDeadline;
    }

    @NonNull
    @Override
    public String toString() {
        String res = "";
        res += "Order id: " + _id  + "\n";
        res += "Order date: " + orderDate + "\n";
        res += "Products:\n";
        for (int i = 0; i < orderProducts.size(); i++) {
            res += orderProducts.get(i).getProductName();
            if (i != orderProducts.size() - 1) res += ", ";
        }
        return res;
    }
}
