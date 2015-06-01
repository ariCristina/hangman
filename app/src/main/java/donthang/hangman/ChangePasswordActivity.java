package donthang.hangman;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class ChangePasswordActivity extends Activity {
    RequestQueue requestQueue;
    JSONObject credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        requestQueue = Volley.newRequestQueue(this);

        final String creds = getIntent().getStringExtra("creds");

        try {
            credentials = new JSONObject(creds);
        } catch (JSONException e) {
            Toast.makeText(ChangePasswordActivity.this, "Json error", Toast.LENGTH_LONG).show();
        }

        final EditText oldPasswordText = (EditText)findViewById(R.id.old_password);
        final EditText newPasswordText = (EditText)findViewById(R.id.new_password);
        Button changePasswordBtn = (Button) findViewById(R.id.btnChangePassword);

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    POSTFunctions.reset_password(ChangePasswordActivity.this, requestQueue,
                            credentials.getString("user_id"), credentials.getString("access_token"),
                            oldPasswordText.getText().toString(), newPasswordText.getText().toString());
                } catch (JSONException e) {
                    Toast.makeText(ChangePasswordActivity.this, "JSON Error calling reset_password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}
