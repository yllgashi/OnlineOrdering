package com.example.onlineordering.ui.products;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlineordering.R;
import com.example.onlineordering.api.ApiService;
import com.example.onlineordering.api.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productsViewModel =
                new ViewModelProvider(this).get(ProductsViewModel.class);

        // get fragment element
        View root = inflater.inflate(R.layout.fragment_products, container, false);

        productsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(@Nullable String s) {
                // initialize widgets
                InitializeList(root);
                InitializeFabMethod(root);
            }
        });

        return root;
    }

    // Initialize list view
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void InitializeList(View fragmentView) {
        ArrayList<Product> products = new ArrayList<Product>();
        ListView listview = (ListView) fragmentView.findViewById(R.id.products_list_view);

        mQueue = Volley.newRequestQueue(fragmentView.getContext());

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

    private void InitializeFabMethod(View fragmentView) {
        // get fab widget
        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById(R.id.fab_products);

        fab.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateProductActivity.class);
                startActivity(intent);
            }
        }));
    }
}