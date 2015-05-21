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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class POSTFunctions {
    public static void login(final Activity activity, RequestQueue requestQueue, String email, String password) {
        String tag_login = "login";
        Map<String,String> jsonParams = new HashMap<String,String>();

        jsonParams.put("email",email);
        jsonParams.put("password", password);

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
                                Intent intent = new Intent(activity,LobbyActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                                Intent intent = new Intent(activity,LobbyActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
}

