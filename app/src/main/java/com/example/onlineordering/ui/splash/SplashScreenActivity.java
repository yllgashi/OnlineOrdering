package com.example.onlineordering.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.onlineordering.MainActivity;
import com.example.onlineordering.R;
import com.example.onlineordering.model.User;
import com.example.onlineordering.ui.auth.LoginActivity;
import com.example.onlineordering.utils.UserPreferences;

public class SplashScreenActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.context = this;

        Log.d("Token", UserPreferences.getInstance().getToken());

        // Create a delay in order to fetch token from local db
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // check if token is saved in Shared preferences
//                if (UserPreferences.getInstance().getToken().length() < 10) {
                if (true) {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                    String email = UserPreferences.getInstance().getUserEmail();
//                    String token = UserPreferences.getInstance().getToken();
//                    intent.putExtra("email", email);
//                    intent.putExtra("token", token);
                    startActivity(intent);
                }

                finish();
            }
        }, 6000);
    }
}