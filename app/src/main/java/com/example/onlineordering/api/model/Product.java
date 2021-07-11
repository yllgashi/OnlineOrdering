package com.example.onlineordering.api.model;

public class Product {
    private String _id;
    private String productName;
    private String productDescription;
    private double productPrice;

    public Product(String productId, String productName, String productDescription, double productPrice) {
        this._id = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public Product(String productName, String productDescription, double productPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public Product() {}

    public String getProductId() {
        return _id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }
}
