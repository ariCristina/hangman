package donthang.hangman;


import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POSTFunctions {
    public static void login(final Activity activity, RequestQueue requestQueue, String email, String password) {
        String tag_login = "login";
        Map<String,String> jsonParams = new HashMap<String,String>();

        jsonParams.put("email","sergiu.caraian@gmail.com");
        jsonParams.put("password", "1234");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                activity.getResources().getString(R.string.rest_url_login),
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonMeta = new JSONObject(response.getString("meta"));
                            if (jsonMeta.getString("status").equals("200")){
                                JSONObject jsonContent = new JSONObject(response.getString("content"));
                                Intent intent = new Intent(activity,LobbyActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("credentials", jsonContent.toString());
                                activity.startActivity(intent);
                                activity.finish();
                            }
                            else {
                                Toast.makeText(activity, "Unregistered user", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag(tag_login);
        requestQueue.add(jsonObjReq);
    }

    public static void logout(final Activity activity, RequestQueue requestQueue, String user_id, String access_token) {
        String tag_login = "logout";
        Map<String,String> jsonParams = new HashMap<String,String>();

        jsonParams.put("user_id",user_id);
        jsonParams.put("access_token", access_token);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                activity.getResources().getString(R.string.rest_url_logout),
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonMeta = new JSONObject(response.getString("meta"));
                            if (jsonMeta.getString("status").equals("200")){
                                Intent intent = new Intent(activity,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                            else {
                                Toast.makeText(activity, "Can't log out", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag(tag_login);
        requestQueue.add(jsonObjReq);
    }

    public static void register(final Activity activity, RequestQueue requestQueue, String email, String password, String nickname) {
        String tag_register = "register";
        Map<String,String> jsonParams = new HashMap<String,String>();

        jsonParams.put("email",email);
        jsonParams.put("password", password);
        jsonParams.put("nickname", nickname);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                activity.getResources().getString(R.string.rest_url_register),
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonMeta = new JSONObject(response.getString("meta"));
                            if (jsonMeta.getString("status").equals("201")){
                                Intent intent = new Intent(activity,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Toast.makeText(activity, "Registered succesfully", Toast.LENGTH_LONG);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                            else {
                                Toast.makeText(activity, "Error registering", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag(tag_register);
        requestQueue.add(jsonObjReq);
    }

    public static void getLobby(final Activity activity, RequestQueue requestQueue,
                                    final List<User> usersList,
                                    final String user_id, final String access_token,
                                    final String filter, final String keyphrase,
                                    final String status) {
        String tag_lobby = "lobby";
        Map<String,String> jsonParams = new HashMap<String,String>();

        jsonParams.put("user_id",user_id);
        jsonParams.put("access_token", access_token);
        jsonParams.put("filter", filter);
        jsonParams.put("keyphrase", keyphrase);
        jsonParams.put("status", status);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                activity.getResources().getString(R.string.rest_url_get_lobby),
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonMeta = new JSONObject(response.getString("meta"));

                            if (jsonMeta.getString("status").equals("200")){
                                JSONArray users = response.getJSONArray("content");

                                for(int i=0; i<users.length(); ++i) {
                                    JSONObject crtUser = users.getJSONObject(i);
                                    usersList.add(new User(crtUser.getString("user_id"),
                                            crtUser.getString("nickname"),
                                            crtUser.getString("status"),
                                            crtUser.getString("avatar"),
                                            crtUser.getString("interests")));
                                }
                            }
                            else {
                                Toast.makeText(activity, "Couldn't fetch lobby", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag(tag_lobby);
        requestQueue.add(jsonObjReq);
    }


    public static void forgot_password(final Activity activity, RequestQueue requestQueue, String email) {
        String tag_register = "forgot_password";
        Map<String,String> jsonParams = new HashMap<String,String>();

        jsonParams.put("email",email);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                activity.getResources().getString(R.string.rest_url_forgot_password),
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonMeta = new JSONObject(response.getString("meta"));
                            if (jsonMeta.getString("status").equals("200")) {
                                Toast.makeText(activity,"A new password has been sent to your email",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(activity, "Error sending new password", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag(tag_register);
        requestQueue.add(jsonObjReq);
    }

    public static void get_profile(final Activity activity, RequestQueue requestQueue, String user_id, String access_token) {
        String tag_register = "register";
        Map<String,String> jsonParams = new HashMap<String,String>();

        jsonParams.put("user_id",user_id);
        jsonParams.put("access_token", access_token);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                activity.getResources().getString(R.string.rest_url_get_profile),
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonMeta = new JSONObject(response.getString("meta"));
                            if (jsonMeta.getString("status").equals("400")){
                                Intent intent = new Intent(activity,ProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("profile",response.toString());
                                activity.startActivity(intent);
                                activity.finish();
                            }
                            else {
                                Toast.makeText(activity, "Error fetching profile", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag(tag_register);
        requestQueue.add(jsonObjReq);
    }

    public static void setChallenge(final Activity activity, RequestQueue requestQueue,
                                    String user_id, String access_token,
                                    String comes_from, String goes_to,
                                    String phrase, String known,
                                    String timer, String tips) {
        String tag_login = "setChlallange";
        Map<String,String> jsonParams = new HashMap<String,String>();

        jsonParams.put("user_id",user_id);
        jsonParams.put("access_token", access_token);
        jsonParams.put("comes_from", comes_from);
        jsonParams.put("goes_to", goes_to);
        jsonParams.put("phrase", phrase);
        jsonParams.put("known", known);
        jsonParams.put("timer", timer);
        jsonParams.put("tips", tips);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                activity.getResources().getString(R.string.rest_url_set_challenge),
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonMeta = new JSONObject(response.getString("meta"));
                            if (jsonMeta.getString("status").equals("200")){
                                Toast.makeText(activity,"Challange sent",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(activity,LobbyActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                            else {
                                Toast.makeText(activity, "Couldn't send challange", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag(tag_login);
        requestQueue.add(jsonObjReq);
    }

    public static void getChallanges(final Activity activity, RequestQueue requestQueue,
                                final String user_id, final String access_token) {
        String tag_getChallanges = "get_challanges";
        Map<String,String> jsonParams = new HashMap<String,String>();

        jsonParams.put("user_id",user_id);
        jsonParams.put("access_token", access_token);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                activity.getResources().getString(R.string.rest_url_notifications_list),
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonMeta = new JSONObject(response.getString("meta"));
                            if (jsonMeta.getString("status").equals("200")){
                                JSONArray notifications = response.getJSONArray("content");

                                // UPDATE THE NOTIFICATION LIST HERE

                            }
                            else {
                                Toast.makeText(activity, "Couldn't fetch lobby", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag(tag_getChallanges);
        requestQueue.add(jsonObjReq);
    }

    public static void init_game(final Activity activity, RequestQueue requestQueue, String user_id,
                                 String access_token, String challenge_id) {
        String tag_login = "login";
        Map<String,String> jsonParams = new HashMap<String,String>();

        jsonParams.put("user_id",user_id);
        jsonParams.put("access_token", access_token);
        jsonParams.put("challenge_id", challenge_id);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                activity.getResources().getString(R.string.rest_url_init_game),
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonMeta = new JSONObject(response.getString("meta"));
                            if (jsonMeta.getString("status").equals("200")){
                                Intent intent = new Intent(activity,GameActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("gameInfo", response.toString());
                                activity.startActivity(intent);
                                activity.finish();
                            }
                            else {
                                Toast.makeText(activity, "Couldn't begin challenge", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag(tag_login);
        requestQueue.add(jsonObjReq);
    }
}

