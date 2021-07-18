package com.example.onlineordering.ui.auth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlineordering.MainActivity;
import com.example.onlineordering.R;
import com.example.onlineordering.api.ApiService;
import com.example.onlineordering.db.DBHelper;
import com.example.onlineordering.model.Product;
import com.example.onlineordering.model.User;
import com.example.onlineordering.ui.splash.SplashScreenActivity;
import com.example.onlineordering.utils.UserPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private DBHelper mydb;
    private Button loginButton;
    private EditText emailText;
    private EditText passwordText;
    private RequestQueue mQueue;
    private Context context;
    private boolean loginRequestIsMade = false;
    private boolean isAuth = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize context
        this.context = this;

        // Initialize UI textboxs
        emailText = findViewById(R.id.text_email);
        passwordText = findViewById(R.id.text_password);
        loginButton = findViewById(R.id.btn_login);

        initializeLoginButton();
    }

    private void initializeLoginButton() {
        // create login button call back method
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Alert")
                            .setMessage("Fill all the field!!...")
                            .setCancelable(false)
                            .setCancelable(true);
                    //Creating dialog box
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                User user = new User(email, password);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                getToken(user);
//                Handler handler = new Handler();
//                handler.postDelayed(
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                if (!loginRequestIsMade)
//                                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
//                                if (isAuth) {
//                                    intent.putExtra("email", email);
////                                    intent.putExtra("token", ApiService.authToken);
//                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                }
//                            }
//                        }, 5000);


            }
        });
    }

    private void getToken(User user) {
        mQueue = Volley.newRequestQueue(context);
        // create json body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", emailText.getText());
            jsonBody.put("password", passwordText.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiService.base_url + "/signin", jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("token");
                            // check if token is returned
                            if (token.length() > 5) {
                                isAuth = true;
                                ApiService.authToken = token;
                            }

                            // check that response is returned from server
//                            loginRequestIsMade = true;

                            // save token in UserPreferences
//                            UserPreferences.getInstance().setToken(token);

                            navigateToMainActivity(user.getEmail());

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Incorrect. Try again", Toast.LENGTH_SHORT).show();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        mQueue.add(request);
    }

    private void navigateToMainActivity(String email) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        intent.putExtra("email", email);
        intent.putExtra("token", ApiService.authToken);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}