package com.example.onlineordering.ui.requestedOrders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.example.onlineordering.model.Order;
import com.example.onlineordering.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestedOrdersFragment extends Fragment {

    private RequestedOrdersViewModel homeViewModel;
    ArrayAdapter<String> adapter;
    private RequestQueue mQueue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(RequestedOrdersViewModel.class);

        // get fragment element
        View root = inflater.inflate(R.layout.fragment_requested_orders, container, false);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        ArrayList<Order> ordersToShow = new ArrayList<Order>();
        ListView listview = (ListView) fragmentView.findViewById(R.id.requested_orders_list_view);

        mQueue = Volley.newRequestQueue(fragmentView.getContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ApiService.base_url + "/orders?orderArrived=false", null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject order = response.getJSONObject(i);

                                // fetch products of order
                                ArrayList<Product> productsOfOrders = new ArrayList<>();
                                JSONArray products = order.getJSONArray("orderProducts");

                                for (int j = 0; j < products.length(); j++) {
                                    JSONObject product = products.getJSONObject(j); //
                                    Product productObject = new Product(
                                            product.getString("productName"),
                                            product.getString("productDescription"),
                                            product.getDouble("productPrice")
                                    );
                                    productsOfOrders.add(productObject);

                                }

                                Order requestedOrderObject = new Order(
                                        order.getString("_id"),
                                        order.getString("orderDate"),
                                        productsOfOrders,
                                        order.getString("orderDeadline")
                                );
                                ordersToShow.add(requestedOrderObject);
                                if (i == response.length() - 1) {
                                    // show data to listview
                                    ArrayList<String> ordersNames = GetOrdersData(ordersToShow);
                                    adapter = new ArrayAdapter<String>(

                                            getActivity(), android.R.layout.simple_list_item_1, ordersNames);
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
                params.put("Content-type", "application/json");
                return params;
            }
        };

        mQueue.add(request);
    }

    // Get title of each products
    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<String> GetOrdersData(ArrayList<Order> requestedOrders) {
        ArrayList<String> requestedOrdersDetails = new ArrayList<String>();
        requestedOrders.forEach((element) -> requestedOrdersDetails.add(element.toString()));

        Log.d("DATA", requestedOrdersDetails.get(0));
        return requestedOrdersDetails;
    }

    private void InitializeFabMethod(View fragmentView) {
        // get fab widget
        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById(R.id.fab_requested_orders);

        fab.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateRequestOrderActivity.class);
                startActivity(intent);
            }
        }));
    }
}