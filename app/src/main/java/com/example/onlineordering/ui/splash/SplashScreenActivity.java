package com.example.onlineordering.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.onlineordering.MainActivity;
import com.example.onlineordering.MyApplication;
import com.example.onlineordering.R;
import com.example.onlineordering.ui.auth.LoginActivity;
import com.example.onlineordering.utils.Constants;
import com.example.onlineordering.utils.UserPreferences;

public class SplashScreenActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.context = this;

        // Create a delay in order to fetch token from local db
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserPreferences.getInstance().getUserAccountId() == -1){
                    Intent intent=new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(SplashScreenActivity.this, MainActivity.class);
                    String email = UserPreferences.getInstance().getUserEmail();
                    intent.putExtra("email", email);
                    startActivity(intent);
                }

                finish();
            }
        },6000);
    }
}