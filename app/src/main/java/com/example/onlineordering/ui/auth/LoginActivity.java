package com.example.onlineordering.ui.auth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

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
import com.example.onlineordering.utils.UserPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private DBHelper mydb;
    private RequestQueue mQueue;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        EditText userName = findViewById(R.id.text_email);
        EditText password = findViewById(R.id.text_password);
        Button loginButton = findViewById(R.id.btn_login);

        // Initialize context
        this.context = this;

        // create login button call back method
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userName.getText().toString();
                String password = userName.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
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

                User user = mydb.loginUser(username, password);
                if (user != null) {
                    UserPreferences.getInstance().setUserAccountId(user.getId());
                    getToken(user);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }

            }
        });
    }

    private void getToken(User user) {
        mQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiService.base_url + "/signin", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject data = null;
                        try {
                            data = response.getJSONObject("");
                            String token = data.getString("token");
                            ApiService.authToken = token;
                            // print token
                            Log.d("MY TOKEN", token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        mQueue.add(request);
    }
}