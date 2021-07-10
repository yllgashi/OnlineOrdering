package com.example.onlineordering.api.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlineordering.api.ApiService;
import com.example.onlineordering.api.model.Product;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductService {
    ApiService apiService;

    public ArrayList<Product> getProducts(Context context) throws JSONException {
        ArrayList<Product> products = new ArrayList<Product>();

//        apiService = new ApiService();
//        RequestParams params = new RequestParams();
//        String data = apiService.get("/products", params);
//
//        Gson gson = new Gson();
//        Product[] productsArray = gson.fromJson(data, Product[].class);
//
//        return  productsArray;

        String url = ApiService.base_url + "/products";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
//        JSONObject jsonBody = new JSONObject("{\"message\": \"Hello\"}");

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                try {
                    JSONArray jsonArray = response.getJSONArray("");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject productJson = jsonArray.getJSONObject(i);
                        Product productObject = new Product(
                                productJson.getString("productId"),
                                productJson.getString("productId"),
                                productJson.getString("productId"),
                                productJson.getDouble("productId")
                        );
                        products.add(productObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                error.printStackTrace();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer " + ApiService.authToken);
                return params;
            }
        };

        // Add the request to the queue
        Volley.newRequestQueue(context).

                add(jsonRequest);

        return products;
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
        } catch (Exception e) {
            //
        }

        apiService.post("/products", params, jsonParams, context);
    }
}
