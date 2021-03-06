package com.example.onlineordering.ui.requestedOrders;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.onlineordering.model.Order;
import com.example.onlineordering.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RequestedOrdersFragment extends Fragment {

    private RequestedOrdersViewModel homeViewModel;
    ArrayAdapter<String> adapter;
    private RequestQueue mQueue;
    private ListView listView;
    private Context context;
    private ArrayList<Order> ordersToShow;
    // Dialog values
    private String productNameFromDialog;
    private int productQuantityFromDialog;
    private String deadlineFromDialog;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(RequestedOrdersViewModel.class);

        // get fragment element
        root = inflater.inflate(R.layout.fragment_requested_orders, container, false);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(@Nullable String s) {

                context = root.getContext();
                // initialize widgets
                InitializeList(root);
                InitializeFabMethod(root);
                InitializeItemOnClickMethod();
            }
        });
        return root;
    }

    // Initialize list view
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void InitializeList(View fragmentView) {
        listView = (ListView) fragmentView.findViewById(R.id.requested_orders_list_view);

        mQueue = Volley.newRequestQueue(fragmentView.getContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ApiService.base_url + "/orders?orderArrived=false", null,
                new Response.Listener<JSONArray>() {
                    ArrayList<Order> fetchedOrders = new ArrayList<Order>();

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
                                fetchedOrders.add(requestedOrderObject);
                                if (i == response.length() - 1) {
                                    // show data to listview
                                    ArrayList<String> ordersNames = GetOrdersData(fetchedOrders);
                                    adapter = new ArrayAdapter<String>(

                                            getActivity(), android.R.layout.simple_list_item_1, ordersNames);
                                    listView.setAdapter(adapter);
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
    private ArrayList<String> GetOrdersData(ArrayList<Order> fetchedOrders) {
        ordersToShow = fetchedOrders;
        ArrayList<String> requestedOrdersDetails = new ArrayList<String>();
        fetchedOrders.forEach((element) -> requestedOrdersDetails.add(element.toString()));

        Log.d("DATA", requestedOrdersDetails.get(0));
        return requestedOrdersDetails;
    }

    private void InitializeFabMethod(View fragmentView) {
        // get fab widget
        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById(R.id.fab_requested_orders);

        fab.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrderDialog();
            }
        }));
    }

    private void InitializeItemOnClickMethod() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                // fetch id of order
                String[] temp = selectedItem.substring(10, selectedItem.length() - 1).split("\n");
                String orderId = temp[0];

                createDialog(orderId);
            }
        });
    }

    private void createDialog(String orderId) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Make order as arrived?");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Your action here
                makeOrderAsArrived(orderId);
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        alert.show();

    }

    private void makeOrderAsArrived(String orderId) {
        Order order = null;
        for (int i = 0; i < ordersToShow.size(); i++) {
            order = ordersToShow.get(i);
            if (order.getOrderId() == orderId) {
                break;
            }
        }

        mQueue = Volley.newRequestQueue(context);
        // create json body
        JSONObject jsonBody = new JSONObject();

        // create json array of products
        JSONArray productsBodyArray = new JSONArray();
        for (int i = 0; i < order.getOrderProducts().size(); i++) {
            // create product JSON object
            JSONObject product = new JSONObject();
            try {
                product.put("productName", order.getOrderProducts().get(i).getProductName());
                product.put("productDescription", order.getOrderProducts().get(i).getProductDescription());
                product.put("productPrice", order.getOrderProducts().get(i).getProductPrice());
                // add JSONObject into JSONArray
                productsBodyArray.put(product);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // initialize json body
        try {
            jsonBody.put("orderDate", order.getOrderDate());
            jsonBody.put("orderProducts", productsBodyArray);
            jsonBody.put("orderDeadline", order.getOrderDeadline());
            jsonBody.put("orderArrived", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, ApiService.base_url + "/orders/" + orderId, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context.getApplicationContext(), "Order updated!", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            InitializeList(root);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), "Incorrect. Try again", Toast.LENGTH_SHORT).show();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("orderArrived", "true");
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

    private void createOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(R.string.create_request_title);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_create_request_order, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.show();
        // initialize edit texts
        final EditText productNameEditText = dialogView.findViewById(R.id.edittext_order_dialog_product_name);
        final EditText productQuantityEditText = dialogView.findViewById(R.id.edittext_order_dialog_product_quantity);
        final EditText deadlineEditText = dialogView.findViewById(R.id.edittext_order_dialog_deadline);

        createDatePickerDialog(deadlineEditText);

        productNameEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        productQuantityEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        Button buttonCreateOrder = dialogView.findViewById(R.id.btn_dialog_create_rorder);
        buttonCreateOrder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                productNameFromDialog = productNameEditText.getText().toString();
                productQuantityFromDialog = Integer.parseInt(productQuantityEditText.getText().toString());
                // we get deadline from calendar picker
                String nowDate = LocalDateTime.now().toString();

                // Create product and make a post request
                ArrayList<Product> products = new ArrayList<Product>();
                products.add(new Product(productNameFromDialog, "", 0));
                Order order = new Order(nowDate.toString(), products, deadlineFromDialog);

                // Send request
                sendPostReq(order);

                alertDialog.dismiss();
            }
        });
        Button buttonCancel = dialogView.findViewById(R.id.btn_dialog_cancel_rorder);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void createDatePickerDialog(EditText dateEditText) {
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(dateEditText, myCalendar);
            }

        };

        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(EditText dateEditText, Calendar myCalendar) {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        dateEditText.setText(sdf.format(myCalendar.getTime()));

        deadlineFromDialog = sdf.format(myCalendar.getTime());
    }


    private void sendPostReq(Order order) {
        mQueue = Volley.newRequestQueue(context);
        // create json body
        JSONObject jsonBody = new JSONObject();

        // create json array of products
        JSONArray productsBodyArray = new JSONArray();
        for (int i = 0; i < order.getOrderProducts().size(); i++) {
            // create product JSON object
            JSONObject product = new JSONObject();
            try {
                product.put("productName", order.getOrderProducts().get(i).getProductName());
                product.put("productDescription", order.getOrderProducts().get(i).getProductDescription());
                product.put("productPrice", order.getOrderProducts().get(i).getProductPrice());
                // add JSONObject into JSONArray
                productsBodyArray.put(product);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // initialize json body
        try {
            jsonBody.put("orderDate", order.getOrderDate());
            jsonBody.put("orderProducts", productsBodyArray);
            jsonBody.put("orderDeadline", order.getOrderDeadline());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiService.base_url + "/orders", jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Order added!", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            InitializeList(root);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Incorrect. Try again", Toast.LENGTH_SHORT).show();

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