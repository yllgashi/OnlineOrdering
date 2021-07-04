package com.example.onlineordering.ui.requestedOrders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RequestedOrdersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RequestedOrdersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is requested orders fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}