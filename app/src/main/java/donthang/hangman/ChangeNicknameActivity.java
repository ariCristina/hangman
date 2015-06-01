package donthang.hangman;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class ChangeNicknameActivity extends Activity {
    RequestQueue requestQueue;
    JSONObject credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nickname);
        requestQueue = Volley.newRequestQueue(this);

        final String creds = getIntent().getStringExtra("creds");

        try {
            credentials = new JSONObject(creds);
        } catch (JSONException e) {
            Toast.makeText(ChangeNicknameActivity.this, "Json error", Toast.LENGTH_LONG).show();
        }

        final EditText nicknameEditText = (EditText)findViewById(R.id.nickname_edit_text);
        Button nicknameEditBtn = (Button) findViewById(R.id.nickname_edit_btn);

        nicknameEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    POSTFunctions.update_nickname(ChangeNicknameActivity.this, requestQueue,
                            credentials.getString("user_id"), credentials.getString("access_token"),
                            nicknameEditText.getText().toString());
                } catch (JSONException e) {
                    Toast.makeText(ChangeNicknameActivity.this,"JSON Error calling update_nickname",Toast.LENGTH_LONG).show();
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
