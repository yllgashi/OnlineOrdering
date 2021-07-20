package com.example.onlineordering.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.onlineordering.MainActivity;
import com.example.onlineordering.R;
import com.example.onlineordering.api.ApiService;
import com.example.onlineordering.ui.auth.LoginActivity;
import com.example.onlineordering.utils.UserPreferences;

public class SplashScreenActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.context = this;
        UserPreferences.sharedPreferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);

        // Create a delay in order to fetch token from local db
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // check if token is saved in Shared preferences
                if (UserPreferences.getToken().length() < 10) {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    String email = UserPreferences.getUserEmail();
                    String token = UserPreferences.getToken();
                    ApiService.authToken = token;

                    intent.putExtra("email", email);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }

                finish();
            }
        }, 2000);
    }
}