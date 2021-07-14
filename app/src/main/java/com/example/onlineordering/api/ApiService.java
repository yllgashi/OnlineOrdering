package com.example.onlineordering.api;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ApiService {
    public static String base_url = "https://online-ordering-express.herokuapp.com";
    //    public static String authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2MGU4ZDNjNjcwZjhkZjUxYjBmODJjZmUiLCJpYXQiOjE2MjU4NzEzMDJ9.dYcma5EGKf94l9VKaxagXCkMzU7a30nZKM28Ar_M6F0";
    public static String authToken = "";

//    public String get(String path, RequestParams params) {
//        final String[] json = new String[1];
//        try {
//            AsyncHttpClient client = new AsyncHttpClient();
//
//            // Headers
//            client.addHeader("Content-Type", "application/json");
//            client.addHeader("Authorization", "Bearer " + authToken);
//
//
//            client.get(base_url + path, new AsyncHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                    json[0] = new String(responseBody);
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    //
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return json[0];
//    }
//
//    public void post(String path, RequestParams params, JSONObject jsonBody, Context context) {
//        // get json body
//        StringEntity jsonEntity = null;
//        try {
//            jsonEntity = new StringEntity(jsonBody.toString());
//        } catch (Exception ex) {
//            //
//        }
//
//
//        AsyncHttpClient client = new AsyncHttpClient();
//
//        // Headers
//        client.addHeader("Content-Type", "application/json");
//        client.addHeader("Authorization", "Bearer " + authToken);
//
//        client.post(context, base_url + path, jsonEntity, "application/json", new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//    }
}
