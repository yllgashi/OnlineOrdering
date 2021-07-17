package com.example.onlineordering.ui.products;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.onlineordering.model.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateProductActivity extends AppCompatActivity {
    private Button createButton;
    private EditText productNameView;
    private EditText productDescriptionView;
    private EditText productPriceView;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        // Initialize UI elements
        createButton = findViewById(R.id.btn_create_product);
        productNameView = findViewById(R.id.text_product_name);
        productDescriptionView = findViewById(R.id.text_product_description);
        productPriceView = findViewById(R.id.text_product_price);
//
        InitializeCreateButtonMethod();
    }

    private void InitializeCreateButtonMethod() {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get text from inputs
                String productName = productNameView.getText().toString();
                String productDesc = productDescriptionView.getText().toString();
                double productPrice = Double.parseDouble(productPriceView.getText().toString());

                if (productName.isEmpty() || productDesc.isEmpty() || productPrice < 0) {
                    return;
                }

                Product product = new Product(productName, productDesc, productPrice);

                sendPostReq(product);

                startActivity(new Intent(CreateProductActivity.this, MainActivity.class));
            }
        });
    }

    private void sendPostReq(Product product) {
        mQueue = Volley.newRequestQueue(this);
        // create json body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("productName", product.getProductName());
            jsonBody.put("productDescription", product.getProductDescription());
            jsonBody.put("productPrice", product.getProductPrice());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiService.base_url + "/products", jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Product added!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Incorrect. Try again", Toast.LENGTH_SHORT).show();

            }
        }) {    //this is the part, that adds the header to the request
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