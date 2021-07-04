package com.example.onlineordering.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineordering.R;

public class ProductsFragment extends Fragment {

    private ProductsViewModel productsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productsViewModel =
                new ViewModelProvider(this).get(ProductsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        productsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}