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

public class CreateChallengeActivity extends Activity {
    static JSONObject jsonRequestInfo;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        requestQueue = Volley.newRequestQueue(this);

        final EditText phraseText = (EditText) findViewById(R.id.phrase);
        final EditText tipsText = (EditText) findViewById(R.id.tips);
        final EditText knownLettersText = (EditText) findViewById(R.id.known_letters);
        Button sendChallengeButton = (Button) findViewById(R.id.btnSendChallange);

        final String challengeInfo = getIntent().getStringExtra("challengeInfo");
        try {
            jsonRequestInfo = new JSONObject(challengeInfo);
            final String user_id = jsonRequestInfo.getString("user_id");
            final String access_token = jsonRequestInfo.getString("access_token");
            final String comes_from = jsonRequestInfo.getString("comes_from");
            final String goes_to = jsonRequestInfo.getString("goes_to");

            sendChallengeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    POSTFunctions.setChallenge(CreateChallengeActivity.this, requestQueue,
                            user_id, access_token, comes_from, goes_to,
                            phraseText.getText().toString(),
                            knownLettersText.getText().toString(),
                            "999999", tipsText.getText().toString());
                }
            });
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"JSON error creating challenge", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel all pending requests
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}
