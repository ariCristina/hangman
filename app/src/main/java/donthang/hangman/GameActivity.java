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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class GameActivity extends Activity {
    static JSONObject jsonGameInfo;
    private int triesRemaining = 7;
    private String phrase = "";
    private String known = "";
    private String tips = "";
    private String auxPhrase = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String gameInfo = getIntent().getStringExtra("gameInfo");

        try {
            jsonGameInfo = new JSONObject(gameInfo);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Json error", Toast.LENGTH_LONG).show();
        }

        try {
            phrase = jsonGameInfo.getString("phrase");
            known = jsonGameInfo.getString("known");
            tips = jsonGameInfo.getString("tips");
        } catch(JSONException e) {
            Toast.makeText(getApplicationContext(),"JSON exception: failed to fetch initial data", Toast.LENGTH_LONG).show();
        }

        if(phrase.length()==0) {
            Toast.makeText(getApplicationContext(),"You Won! The word is _",Toast.LENGTH_LONG).show();
            finish();
        }

        final TextView triesTextField = (TextView) findViewById(R.id.tries_remaining_text);
        final TextView phraseTextField = (TextView) findViewById(R.id.phrase_text);
        final TextView knownTextField = (TextView) findViewById(R.id.known_text);
        final TextView tipsTextField = (TextView) findViewById(R.id.tips_text);
        final EditText inputTextField = (EditText) findViewById(R.id.letter_edit_text);
        final Button inputButton = (Button) findViewById(R.id.letterBtn);

        // Check if there are any undiscovered letters
        auxPhrase = phrase;
        for(int i = 0; i<known.length(); ++i) {
            auxPhrase = auxPhrase.replace(""+known.charAt(i),"");
        }
        if(auxPhrase.equals("")) {
            Toast.makeText(getApplicationContext(),"You Won! The word is " + phrase,Toast.LENGTH_LONG).show();
            finish();
        }

        triesTextField.setText("Tries remaining : " + Integer.toString(triesRemaining));
        auxPhrase = phrase;
        for(int i = 0; i<auxPhrase.length(); ++i) {
           if(known.indexOf(auxPhrase.charAt(i))==-1) {
               char[] auxCharVector = auxPhrase.toCharArray();
               auxCharVector[i] = '_';
               auxPhrase = String.valueOf(auxCharVector);
           }
        }
        phraseTextField.setText("Phrase : " + auxPhrase);
        knownTextField.setText("Known letters : " + known);
        tipsTextField.setText("Tips : " + tips);

        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newLetter = inputTextField.getText().toString();
                if(newLetter.equals("") || known.indexOf(newLetter.charAt(0))!=-1) {
                    Toast.makeText(getApplicationContext(),"Please enter a new letter",Toast.LENGTH_LONG).show();
                }
                else {
                    triesRemaining--;
                    known = known + newLetter.charAt(0);

                    // Check for victory
                    auxPhrase = phrase;
                    for(int i = 0; i<known.length(); ++i) {
                        auxPhrase = auxPhrase.replace(""+known.charAt(i),"");
                    }
                    if(auxPhrase.equals("")) {
                        Toast.makeText(getApplicationContext(),"You Won! The word is " + phrase,Toast.LENGTH_LONG).show();
                        finish();
                    }

                    // Check for lose
                    if(triesRemaining == 0) {
                        Toast.makeText(getApplicationContext(),"You Lost! The word was " + phrase,Toast.LENGTH_LONG).show();
                        finish();
                    }

                    // Update Views
                    triesTextField.setText("Tries remaining : " + Integer.toString(triesRemaining));
                    auxPhrase = phrase;
                    for(int i = 0; i<auxPhrase.length(); ++i) {
                        if(known.indexOf(auxPhrase.charAt(i))==-1) {
                            char[] auxCharVector = auxPhrase.toCharArray();
                            auxCharVector[i] = '_';
                            auxPhrase = String.valueOf(auxCharVector);
                        }
                    }
                    phraseTextField.setText("Phrase : " + auxPhrase);
                    knownTextField.setText("Known letters : " + known);
                    tipsTextField.setText("Tips : " + tips);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
