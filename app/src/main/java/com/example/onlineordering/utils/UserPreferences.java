package com.example.onlineordering.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.onlineordering.ui.splash.SplashScreenActivity;

public class UserPreferences {
    //    private static UserPreferences instance;
    public static SharedPreferences sharedPreferences;


    public static void setUserEmail(String email) {
        sharedPreferences.edit().putString(Constants.USER_EMAIL, email).apply();
    }

    public static String getUserEmail() {
        return sharedPreferences.getString(Constants.USER_EMAIL, "Not available");
    }

    public static void setToken(String token) {
        sharedPreferences.edit().putString(Constants.USER_ACCESS_TOKEN, token).apply();
    }

    // get token
    public static String getToken() {
        return sharedPreferences.getString(Constants.USER_ACCESS_TOKEN, "");
    }
}
