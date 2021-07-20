//package com.example.onlineordering.api.services;
//
//import android.content.Context;
//
//import com.example.onlineordering.api.ApiService;
//import com.example.onlineordering.model.Order;
//import com.example.onlineordering.model.Product;
//import com.google.gson.Gson;
//import com.loopj.android.http.RequestParams;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//public class OrderService {
//
//    ApiService apiService;
//
//    public Order[] getRequestedOrders() {
//        apiService = new ApiService();
//        RequestParams params = new RequestParams();
//        params.put("orderArrived", false);
//
//        String data = apiService.get("/orders", params);
//
//        Gson gson = new Gson();
//        Order[] requestedOrdersArray = gson.fromJson(data, Order[].class);
//
//        return requestedOrdersArray;
//    }
//
//    public Order[] getArrivedOrders() {
//        apiService = new ApiService();
//        RequestParams params = new RequestParams();
//        params.put("orderArrived", true);
//
//        String data = apiService.get("/orders", params);
//
//        Gson gson = new Gson();
//        Order[] requestedOrdersArray = gson.fromJson(data, Order[].class);
//
//        return requestedOrdersArray;
//    }
//
//
//    public void createOrder(Order order, Context context) {
//        apiService = new ApiService();
//        RequestParams params = new RequestParams();
//
//        JSONObject jsonParams = new JSONObject();
//        try {
//            jsonParams.put("_id", order.getOrderId());
//            jsonParams.put("orderDate", order.getOrderDate());
//            jsonParams.put("orderDeadline", order.getOrderDeadline());
//            jsonParams.put("orderProducts", order.getOrderProducts());
//        } catch (Exception e) {
//            //
//        }
//
//        apiService.post("/orders", params, jsonParams, context);
//    }
//}
