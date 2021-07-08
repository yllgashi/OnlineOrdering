package com.example.onlineordering.ui.requestedOrders;

import android.annotation.SuppressLint;
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

import com.example.onlineordering.R;
import com.example.onlineordering.data.StaticData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RequestedOrdersFragment extends Fragment {

    private RequestedOrdersViewModel homeViewModel;
    ArrayAdapter<String> adapter;

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
        ListView listview = (ListView) fragmentView.findViewById(R.id.requested_orders_list_view);

        ArrayList<String> requestedOrders = GetOrdersData();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, requestedOrders);

        listview.setAdapter(adapter);
    }

    // Get title of each products
    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<String> GetOrdersData() {
        ArrayList<String> requestedOrdersDetails = new ArrayList<String>();
        StaticData.requestedOrders.forEach((element) -> requestedOrdersDetails.add(element.toString()));

        return requestedOrdersDetails;
    }

    private void InitializeFabMethod(View fragmentView) {
        // get fab widget
        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById(R.id.fab_requested_orders);

        fab.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Requested orders", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }));
    }
}