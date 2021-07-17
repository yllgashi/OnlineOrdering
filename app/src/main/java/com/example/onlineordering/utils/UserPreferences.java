package com.example.onlineordering.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.onlineordering.ui.splash.SplashScreenActivity;

public class UserPreferences {
    private static UserPreferences instance;
    private SharedPreferences sharedPreferences;

    public static UserPreferences getInstance() {
        if (instance == null) {
            instance = new UserPreferences();
        }

        return instance;
    }


    private UserPreferences() {
        instance = this;
        sharedPreferences = SplashScreenActivity.context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE);

    }

//
//    public int getUserAccountId() {
//        return sharedPreferences.getInt(Constants.USER_ACCOUNT_ID, -1);
//    }
//
//
//    public void setUserAccountId(int accountId) {
//        sharedPreferences.edit().putInt(Constants.USER_ACCOUNT_ID, accountId).commit();
//    }
////
//    public String getUserEmail() {
//        return sharedPreferences.getString(Constants.USER_USERNAME, "Not available");
//    }

    // set token
    public void setToken(String token) {
        sharedPreferences.edit().putString(Constants.USER_ACCESS_TOKEN, token).commit();
    }

    // get token
    public String getToken() {
        return sharedPreferences.getString(Constants.USER_ACCESS_TOKEN, "");
    }
}
