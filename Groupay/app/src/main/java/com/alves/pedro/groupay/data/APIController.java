package com.alves.pedro.groupay.data;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alves.pedro.groupay.model.CreditCard;
import com.alves.pedro.groupay.model.User;
import com.alves.pedro.groupay.utils.GsonUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class APIController {

    private static APIController mInstance;

    private static final String API_URL = "http://b95d3955.ngrok.io/api/";
    private static final String USERS = "users/";
    private static final String CARDS = "cards/";
    private static final String ASSOCIATE = "associate/";
    private static final String CREATE = "create";

    public static final int REQUEST_RESULT_OK = 1;
    public static final int REQUEST_RESULT_ERROR = 2;

    public static APIController getInstance() {
        if (mInstance == null)
            mInstance = new APIController();
        return mInstance;
    }

    public void registerUser(User user, Context context, Handler handler) {
        if (user == null)
            return;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, API_URL + USERS + CREATE,
                response -> {
                    User userResult = GsonUtils.getInstance()
                            .fromJson(new String(response.getBytes()), User.class);
                    handleSuccessMessage(handler, userResult);
                },
                error -> handleErrorMessage(handler)
        ) {
            @Override
            public byte[] getBody() {
                String userJson = GsonUtils.getInstance().toJson(user);
                Log.e("POST", userJson);
                return userJson.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() {
                return getHeadersParam();
            }
        };
        queue.add(postRequest);
    }

    public void registerCreditCard(CreditCard creditCard, User user, Context context, Handler handler) {
        if (user == null || user.getId() == null)
            return;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, API_URL + CARDS + ASSOCIATE + user.getId(),
                response -> handleSuccessMessage(handler, null),
                error -> handleErrorMessage(handler)
        ) {
            @Override
            public byte[] getBody() {
                String userJson = GsonUtils.getInstance().toJson(creditCard);
                Log.e("POST", userJson);
                return userJson.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() {
                return getHeadersParam();
            }
        };
        queue.add(postRequest);
    }

    private void handleSuccessMessage(Handler handler, Object obj) {
        Message message = new Message();
        message.what = REQUEST_RESULT_OK;
        message.obj = obj;
        handler.handleMessage(message);
    }

    private void handleErrorMessage(Handler handler) {
        Message message = new Message();
        message.what = REQUEST_RESULT_ERROR;
        handler.handleMessage(message);
    }

    private HashMap<String, String> getHeadersParam() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }


//    RequestQueue queue = Volley.newRequestQueue(this);
//    String url ="http://www.google.com";
//
//    // Request a string response from the provided URL.
//    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    // Display the first 500 characters of the response string.
//                    mTextView.setText("Response is: "+ response.substring(0,500));
//                }
//            }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            mTextView.setText("That didn't work!");
//        }
//    });
//
//// Add the request to the RequestQueue.
//queue.add(stringRequest);
}
