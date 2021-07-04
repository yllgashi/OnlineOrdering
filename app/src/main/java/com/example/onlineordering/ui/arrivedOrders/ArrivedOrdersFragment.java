package com.example.onlineordering.ui.arrivedOrders;

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

public class ArrivedOrdersFragment extends Fragment {

    private ArrivedOrdersViewModel arrivedOrdersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        arrivedOrdersViewModel =
                new ViewModelProvider(this).get(ArrivedOrdersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_arrived_orders, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        arrivedOrdersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}