package com.example.onlineordering.api.services;
import android.content.Context;
import com.example.onlineordering.api.ApiService;
import com.example.onlineordering.api.model.Product;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;
import java.util.ArrayList;

public class ProductService {
    ApiService apiService;

    public Product[] getProoducts() {
        apiService = new ApiService();
        RequestParams params = new RequestParams();
        String data = apiService.get("/products", params);

        Gson gson = new Gson();
        Product[] productsArray = gson.fromJson(data, Product[].class);

        return  productsArray;
    }



    public void createProduct(Product product, Context context) {
        apiService = new ApiService();
        RequestParams params = new RequestParams();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("_id", product.getProductId());
            jsonParams.put("productName", product.getProductName());
            jsonParams.put("productDescription", product.getProductDescription());
            jsonParams.put("productPrice", product.getProductPrice());
        }
        catch (Exception e) {
            //
        }

        apiService.post("/products", params, jsonParams, context);
    }
}
