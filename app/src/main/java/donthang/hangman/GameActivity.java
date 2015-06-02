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
    boolean wonGame = false;
    boolean lostGame = false;

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
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"Error JSON",Toast.LENGTH_LONG).show();
        }

        if (emptyPhrase(phrase)) {
            Toast.makeText(getApplicationContext(), "You Won! The word is _", Toast.LENGTH_LONG).show();
            finish();
        }

        final TextView triesTextField = (TextView) findViewById(R.id.tries_remaining_text);
        final TextView phraseTextField = (TextView) findViewById(R.id.phrase_text);
        final TextView knownTextField = (TextView) findViewById(R.id.known_text);
        final TextView tipsTextField = (TextView) findViewById(R.id.tips_text);
        final EditText inputTextField = (EditText) findViewById(R.id.letter_edit_text);
        final Button inputButton = (Button) findViewById(R.id.letterBtn);

        // Check if there are any undiscovered letters
        if (victoryCondition(phrase,known)) {
            Toast.makeText(getApplicationContext(), "You Won! The word is " + phrase, Toast.LENGTH_LONG).show();
            finish();
        }

        triesTextField.setText("Tries remaining : " + Integer.toString(triesRemaining));

        // Set phrase blanks
        String auxPhrase = setPhraseBlanks(phrase,known);

        phraseTextField.setText("Phrase : " + auxPhrase);
        knownTextField.setText("Known letters : " + known);
        tipsTextField.setText("Tips : " + tips);

        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newLetter = inputTextField.getText().toString();
                if (!checkNewLetter(newLetter,known)) {
                    Toast.makeText(getApplicationContext(), "Please enter a new letter", Toast.LENGTH_LONG).show();
                } else {
                    triesRemaining--;
                    known = known + newLetter.charAt(0);

                    // Check for lose
                    if (loseCondition(triesRemaining)) {
                        Toast.makeText(getApplicationContext(), "You Lost! The word was " + phrase, Toast.LENGTH_LONG).show();
                        finish();
                    }

                    // Check for victory
                    if(victoryCondition(phrase, known))
                    {
                        Toast.makeText(getApplicationContext(), "You Won! The word is " + phrase, Toast.LENGTH_LONG).show();
                        finish();
                    }

                    // Update Views
                    triesTextField.setText("Tries remaining : " + Integer.toString(triesRemaining));
                    String auxPhrase = setPhraseBlanks(phrase,known);

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

    public boolean emptyPhrase(String phrase) {
        if(phrase.length()==0) {
            wonGame = true;
            return true;
        }
        return false;
    }

    /*@ public normal_behavior
        requires phraseArg!=null && knownArg!=null
        ensures wonGame=true
    */
    public boolean victoryCondition(String phraseArg, String knownArg) {

        for (int i = 0; i < knownArg.length(); ++i) {
            phraseArg = phraseArg.replace("" + knownArg.charAt(i), "");
        }
        if (phraseArg.equals("")) {
            wonGame = true;
            return true;
        }
        else return false;
    }

    /*@ public normal_behavior
        requires phraseArg!=null && knownArg!=null
        ensures setPhraseBlanks
    */
    public String setPhraseBlanks(String phraseArg, String knownArg) {
        for (int i = 0; i < phraseArg.length(); ++i) {
            if (known.indexOf(phraseArg.charAt(i)) == -1) {
                char[] auxCharVector = phraseArg.toCharArray();
                auxCharVector[i] = '_';
                phraseArg = String.valueOf(auxCharVector);
            }
        }
        return phraseArg;
    }

    /*@ public normal_behavior
        requires newLetterArg!=null && knownArg!=null
        ensures newLetterArg==true
    */
    public boolean checkNewLetter(String newLetterArg, String knownArg) {
        if(newLetterArg.equals("") || knownArg.indexOf(newLetterArg.charAt(0)) != -1)
            return false;
        return true;
    }

    /*@ public normal_behavior
        requires triseRmainingArg>0
        ensures lostGame==true
    */
    public boolean loseCondition(int triesRemainingArg) {
        if(triesRemainingArg<=0) {
            lostGame = true;
            return true;
        }
        return false;
    }
}