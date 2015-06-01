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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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


public class LobbyActivity extends Activity implements AdapterView.OnItemClickListener {
    static JSONObject credentials;
    RequestQueue requestQueue;
    List<User> usersList = new ArrayList<User>();
    LobbyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        requestQueue = Volley.newRequestQueue(this);

        final String creds = getIntent().getStringExtra("credentials");

        try {
            credentials = new JSONObject(creds);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Json error", Toast.LENGTH_LONG).show();
        }

        final Button searchButton = (Button) findViewById(R.id.btnSearch);
        final EditText searchEditText = (EditText) findViewById(R.id.search);
        final ListView lobbyList = (ListView) findViewById(R.id.lobby_list);
        final Button logoutButton = (Button) findViewById(R.id.logout_button);
        final Button profileButton = (Button) findViewById(R.id.profile_button);

        ListView userListView = (ListView) findViewById(R.id.lobby_list);
        adapter = new LobbyListAdapter(this,usersList);
        userListView.setAdapter(adapter);
        userListView.setOnItemClickListener(this);

        try {
            POSTFunctions.getLobby(LobbyActivity.this, requestQueue, adapter, usersList,
                    credentials.getString("user_id"), credentials.getString("access_token"),
                    "2", "", "online");

        } catch(JSONException e) {
            Toast.makeText(getApplicationContext(),"Json error requesting lobby", Toast.LENGTH_LONG).show();
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    String searchMode = "3";
                    if(searchEditText.getText().toString().equals(""))
                        searchMode = "2";

                    POSTFunctions.getLobby(LobbyActivity.this, requestQueue, adapter, usersList,
                            credentials.getString("user_id"), credentials.getString("access_token"),
                            searchMode, searchEditText.getText().toString(), "online");

                } catch(JSONException e) {
                    Toast.makeText(getApplicationContext(),"Json error requesting lobby", Toast.LENGTH_LONG).show();
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    POSTFunctions.logout(LobbyActivity.this, requestQueue, credentials.getString("user_id"), credentials.getString("access_token"));
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Json error: couldn't log out", Toast.LENGTH_LONG).show();
                }
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LobbyActivity.this,ProfileActivity.class);
                intent.putExtra("credentials",creds);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User selectedUser = usersList.get(position);

        try {
            Intent intent = new Intent(LobbyActivity.this,CreateChallengeActivity.class);
            Map<String,String> jsonChallengeInfo = new HashMap<String,String>();

            jsonChallengeInfo.put("user_id", credentials.getString("user_id"));
            jsonChallengeInfo.put("access_token", credentials.getString("access_token"));
            jsonChallengeInfo.put("comes_from", credentials.getString("user_id"));
            jsonChallengeInfo.put("goes_to", selectedUser.getUser_id());
            JSONObject challengeInfo = new JSONObject(jsonChallengeInfo);
            intent.putExtra("challengeInfo",challengeInfo.toString());
            startActivity(intent);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"Couldn't challange user (JSON Error)", Toast.LENGTH_LONG).show();
        }
    }

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

