package com.example.onlineordering.ui.products;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlineordering.R;
import com.example.onlineordering.api.ApiService;
import com.example.onlineordering.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductsFragment extends Fragment {

    private ProductsViewModel productsViewModel;
    ArrayAdapter<String> adapter;
    private RequestQueue mQueue;
    private View root;
    // Dialog values
    private String productNameFromDialog;
    private String productDescriptionFromDialog;
    private double productPriceFromDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productsViewModel =
                new ViewModelProvider(this).get(ProductsViewModel.class);

        // get fragment element
        root = inflater.inflate(R.layout.fragment_products, container, false);

        productsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(@Nullable String s) {

                // initialize UI widgets
                InitializeList();
                InitializeFabMethod();
            }
        });

        return root;
    }

    // Initialize list view
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void InitializeList() {
        ArrayList<Product> products = new ArrayList<Product>();
        ListView listview = (ListView) root.findViewById(R.id.products_list_view);

        mQueue = Volley.newRequestQueue(root.getContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ApiService.base_url + "/products", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject product = response.getJSONObject(i);
                                Product productObject = new Product(
                                        product.getString("_id"),
                                        product.getString("productName"),
                                        product.getString("productDescription"),
                                        product.getDouble("productPrice")
                                );
                                products.add(productObject);
                                if (i == response.length() - 1) {
                                    // show data to listview
                                    ArrayList<String> productsNames = GetProductsName(products);
                                    adapter = new ArrayAdapter<String>(

                                            getActivity(), android.R.layout.simple_list_item_1, productsNames);
                                    listview.setAdapter(adapter);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + ApiService.authToken);
                params.put("content-type", "application/json");
                return params;
            }
        };

        mQueue.add(request);
    }

    // Get title of each products
    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<String> GetProductsName(ArrayList<Product> products) {
        ArrayList<String> productNames = new ArrayList<String>();
        products.forEach((element) -> productNames.add(element.getProductName()));

        return productNames;
    }

    private void InitializeFabMethod() {
        // get fab widget
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab_products);

        fab.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), CreateProductActivity.class);
//                startActivity(intent);
                createDialog();
            }
        }));
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
        builder.setCancelable(false);
        builder.setTitle(R.string.create_product_title);
        final View dialogView = LayoutInflater.from(root.getContext()).inflate(R.layout.create_product_dialog, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.show();
        // initialize edit texts
        final EditText productNameEditText = dialogView.findViewById(R.id.create_product_name_input);
        final EditText productDescriptionEditText = dialogView.findViewById(R.id.create_product_description_input);
        final EditText productPriceEditText = dialogView.findViewById(R.id.create_product_price_input);

        productNameEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        productDescriptionEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        productPriceEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        Button buttonLogin = dialogView.findViewById(R.id.btn_dialog_create_product);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productNameFromDialog = productNameEditText.getText().toString();
                productDescriptionFromDialog = productDescriptionEditText.getText().toString();
                productPriceFromDialog = Double.parseDouble(productPriceEditText.getText().toString());

                // Create product and make a post request
                Product product = new Product(productNameFromDialog, productDescriptionFromDialog, productPriceFromDialog);
                createProductReq(product);

                alertDialog.dismiss();
            }
        });
        Button buttonCancel = dialogView.findViewById(R.id.btn_dialog_cancel_product);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private void createProductReq(Product product) {
        mQueue = Volley.newRequestQueue(root.getContext());
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
                        Toast.makeText(root.getContext(), "Product added!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(root.getContext(), "Incorrect. Try again", Toast.LENGTH_SHORT).show();

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