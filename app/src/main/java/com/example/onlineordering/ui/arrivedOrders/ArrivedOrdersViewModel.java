package com.example.onlineordering.ui.arrivedOrders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ArrivedOrdersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ArrivedOrdersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is arrived orders fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}