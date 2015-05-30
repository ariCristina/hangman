package donthang.hangman;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;


public class CreateChallengeActivity extends Activity {
    static JSONObject jsonRequestInfo;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        String requestInfo = getIntent().getStringExtra("requestInfo");
        try {
            jsonRequestInfo = new JSONObject(requestInfo);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Json error", Toast.LENGTH_LONG).show();
        }

        // Enter challange parameters here
        String phrase = null;
        String tips = null;
        String known = null;
        String timer = null;

        // Send request
        try {
            POSTFunctions.setChallange(CreateChallengeActivity.this, requestQueue,
                    jsonRequestInfo.getString("user_id"), jsonRequestInfo.getString("access_token"),
                    jsonRequestInfo.getString("comes_from"), jsonRequestInfo.getString("goes_to"),
                    phrase, known, timer, tips);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error sending challange", Toast.LENGTH_LONG);
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_challenge, menu);
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
}
