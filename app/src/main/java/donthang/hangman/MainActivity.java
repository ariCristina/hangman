package donthang.hangman;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    CallbackManager callbackManager;
    public static final String PREFS_NAME = "preferences";
    private LoginButton loginButton;
    RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        // Initialize the request queue
        requestQueue = Volley.newRequestQueue(this);

        // Check if user is already logged in with facebook
        if (false) {//isLoggedInWithFacebook()) {
            // Send REST login

            Intent intent = new Intent(this, LobbyActivity.class);
            startActivity(intent);
            finish();
        } else {
            callbackManager = CallbackManager.Factory.create();
            loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.clearPermissions();
            loginButton.setReadPermissions("user_friends");

            // Register facebook button callback

            loginButton.registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            // App code
                            Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancel() {
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            Toast.makeText(getApplicationContext(), "Error logging in with Facebook", Toast.LENGTH_LONG).show();
                        }
                    });

            // Register
            Button signupButton = (Button) findViewById(R.id.signup_button);
            signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            // Sign in
            Button signinButton = (Button) findViewById(R.id.btnJoin);
            final EditText emailEditText = (EditText)findViewById(R.id.email);
            final EditText passwordEditText = (EditText)findViewById(R.id.password);

            signinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    POSTFunctions.login(MainActivity.this, requestQueue, emailEditText.getText().toString(), passwordEditText.getText().toString());
            }});


            // Forgot Password
            Button forgotPasswordButton = (Button) findViewById(R.id.btnForgotPassword);
            forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //REST forgot password

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    public boolean isLoggedInWithFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


}
