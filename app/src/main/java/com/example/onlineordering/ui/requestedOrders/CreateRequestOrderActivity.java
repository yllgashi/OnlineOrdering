package com.example.onlineordering.ui.requestedOrders;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlineordering.MainActivity;
import com.example.onlineordering.R;
import com.example.onlineordering.api.ApiService;
import com.example.onlineordering.model.Order;
import com.example.onlineordering.model.Product;
import com.example.onlineordering.ui.products.CreateProductActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateRequestOrderActivity extends AppCompatActivity {
    private Button createButton;
    private EditText orderDeadlineView;
    private EditText orderProductNameView;
    private EditText orderQuantityView;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request_order);

        // Initialize UI elements
        createButton = findViewById(R.id.btn_create_order);
        orderDeadlineView = findViewById(R.id.text_order_deadline);
        orderProductNameView = findViewById(R.id.text_order_product_name);
        orderQuantityView = findViewById(R.id.text_order_quantity);

        InitializeCreateButtonMethod();
    }

    private void InitializeCreateButtonMethod() {
        createButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // get text from inputs
                String orderDeadline = orderDeadlineView.getText().toString();
                int orderQuantity = Integer.parseInt(orderQuantityView.getText().toString());
                String orderProductName = orderProductNameView.getText().toString();
                String nowDate = LocalDateTime.now().toString();

                Log.d("DATEEEEE", nowDate.toString());

                if (orderDeadline.isEmpty() || orderQuantity < 1) {
                    return;
                }

                ArrayList<Product> products = new ArrayList<Product>();
                products.add(new Product(orderProductName, "", 0));

                Order order = new Order(nowDate.toString(), products, orderDeadline);

                sendPostReq(order);

                startActivity(new Intent(CreateRequestOrderActivity.this, MainActivity.class));
            }
        });
    }

    private void sendPostReq(Order order) {
        mQueue = Volley.newRequestQueue(this);
        // create json body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("orderDate", order.getOrderDate());
            jsonBody.put("orderProducts", order.getOrderProducts());
            jsonBody.put("orderDeadline", order.getOrderDeadline());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiService.base_url + "/orders", jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Order added!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Incorrect. Try again", Toast.LENGTH_SHORT).show();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("orderArrived", "false");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + ApiService.authToken);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        mQueue.add(request);
    }
}