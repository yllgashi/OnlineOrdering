package com.example.onlineordering.ui.arrivedOrders;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

public class ArrivedOrdersFragment extends Fragment {

    private ArrivedOrdersViewModel arrivedOrdersViewModel;
    ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        arrivedOrdersViewModel =
                new ViewModelProvider(this).get(ArrivedOrdersViewModel.class);

        // get fragment element
        View root = inflater.inflate(R.layout.fragment_arrived_orders, container, false);

        arrivedOrdersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        ListView listview = (ListView) fragmentView.findViewById(R.id.arrived_orders_list_view);

        ArrayList<String> arrivedOrders = GetOrdersData();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrivedOrders);

        listview.setAdapter(adapter);
    }

    // Get title of each products
    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<String> GetOrdersData() {
        ArrayList<String> arrivedOrdersDetails = new ArrayList<String>();
//        StaticData.arrivedOrders.forEach((element) -> arrivedOrdersDetails.add(element.toString()));

        return arrivedOrdersDetails;
    }

    private void InitializeFabMethod(View fragmentView) {
        // get fab widget
        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById(R.id.fab_arrived_orders);

        fab.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Arrived orders", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }));
    }
}