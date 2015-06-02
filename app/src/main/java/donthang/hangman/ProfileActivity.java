package donthang.hangman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileActivity extends Activity implements AdapterView.OnItemClickListener {
    static JSONObject credentials;
    RequestQueue requestQueue;
    List<Challenge> challengeList = new ArrayList<Challenge>();
    ChallengeListAdapter adapter;

    /*@ protected normal_behavior
    requires requestQueue!=null
    ensures getChallenges()==challengeList
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        requestQueue = Volley.newRequestQueue(this);

        final String creds = getIntent().getStringExtra("credentials");
        TextView nicknameTextView = (TextView) findViewById(R.id.nickname_text_view);

        try {
            credentials = new JSONObject(creds);
            nicknameTextView.setText(credentials.getString("nickname"));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Json error", Toast.LENGTH_LONG).show();
        }

        ListView challengeListView = (ListView) findViewById(R.id.challenge_list);
        final Button changeNicknameButton = (Button) findViewById(R.id.change_nickname_button);
        final Button changePasswordButton = (Button) findViewById(R.id.change_password_button);

        adapter = new ChallengeListAdapter(this,challengeList);
        challengeListView.setAdapter(adapter);
        challengeListView.setOnItemClickListener(this);

        try {
            POSTFunctions.getChallenges(ProfileActivity.this, requestQueue, challengeList, adapter,
                    credentials.getString("user_id"), credentials.getString("access_token"));

        } catch(JSONException e) {
            Toast.makeText(getApplicationContext(),"Json error requesting challenges", Toast.LENGTH_LONG).show();
        }

        changeNicknameButton.setOnClickListener(new View.OnClickListener() {
            /*@ public normal_behavior
                requires onClick(View v)==true
                ensures ChangeActivityName()=startActivity(ChangeActivityName)
            */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,ChangeNicknameActivity.class);
                intent.putExtra("creds",credentials.toString());
                startActivity(intent);
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            /*@ public normal_behavior
                requires onClick(View v)==true
                ensures ChangePasswordActivity()=startActivity(ChangePasswordActivity)
            */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,ChangePasswordActivity.class);
                intent.putExtra("creds",credentials.toString());
                startActivity(intent);
            }
        });
    }

    /*@ public normal_behvaior
        requires parent!=null && view!=null && position>0 && id>0
        ensures selectChallenge==true
    */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Challenge selectedChallenge = challengeList.get(position);

        try {
            POSTFunctions.init_game(this, requestQueue, credentials.getString("user_id"), credentials.getString("access_token"),
                    selectedChallenge.getChallengeId(), selectedChallenge.getNotificationId());
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"Couldn't enter challenge (JSON Error)", Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onDestroy();
    }
}
