package com.example.onlineordering.ui.products;

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
import com.example.onlineordering.data.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

    private ProductsViewModel productsViewModel;
    ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productsViewModel =
                new ViewModelProvider(this).get(ProductsViewModel.class);

        // get fragment element
        View root = inflater.inflate(R.layout.fragment_products, container, false);

        // get listview widget
        ListView listview = (ListView) root.findViewById(R.id.products_list_view);

        productsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(@Nullable String s) {
                // initialize listview
                InitializeList(root);
            }
        });

        return root;
    }

    // Initialize list view
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void InitializeList(View fragmentView) {
        ListView listview = (ListView) fragmentView.findViewById(R.id.products_list_view);

        ArrayList<String> productsNames = GetProductsName();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, productsNames);

        listview.setAdapter(adapter);
    }

    // Get title of each products
    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private  ArrayList<String> GetProductsName() {
        ArrayList<String> productNames = new ArrayList<String>();
        StaticData.products.forEach((element) -> productNames.add(element.getProductName()));

        return  productNames;
    }
}