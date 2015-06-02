package donthang.hangman;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sergiu on 6/2/2015.
 */
public class GameActivityTest extends ActivityInstrumentationTestCase2<GameActivity> {
    private GameActivity originalActivity;

    public GameActivityTest() {
        super(GameActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        originalActivity = getActivity();
    }

    @Test
    public void testEmptyPhrase() throws Exception {
        String testPhrase = "123";

        final boolean result = originalActivity.emptyPhrase(testPhrase);
        final boolean expected = false;
        assertEquals(result,expected);
    }

    @Test
    public void testVictoryCondition() throws Exception {
        String phrase = "asdda";
        String known = "aasaaad";
        final boolean result = originalActivity.victoryCondition(phrase, known);
        final boolean expected = true;
        assertEquals(result,expected);

    }

    @Test
    public void test1VictoryCondition() throws Exception {
        String phrase = "asdda";
        String known = "";
        final boolean result = originalActivity.victoryCondition(phrase,known);
        final boolean expected = false;
        assertEquals(result,expected);

    }

    @Test
    public void test2VictoryCondition() throws Exception {
        String phrase = "";
        String known = "aasaaad";
        final boolean result = originalActivity.victoryCondition(phrase,known);
        final boolean expected = true;
        assertEquals(result,expected);

    }

    @Test
    public void testSetPhraseBlanks() throws Exception {
        String phrase = "asd1sqa";
        String known = "as";
        final String result = originalActivity.setPhraseBlanks(phrase, known);
        final String expected =  "__d1_q_";
        assertEquals(result,expected);
    }

    @Test
    public void test1SetPhraseBlanks() throws Exception {
        String phrase = "";
        String known = "asdasd";
        final String result = originalActivity.setPhraseBlanks(phrase,known);
        final String expected =  "";
        assertEquals(result,expected);
    }

    @Test
    public void test2SetPhraseBlanks() throws Exception {
        String phrase = "asd1sqa";
        String known = "";
        final String result = originalActivity.setPhraseBlanks(phrase,known);
        final String expected =  "asd1sqa";
        assertEquals(result,expected);
    }

    @Test
    public void testCheckNewLetter() throws Exception {
        String newLetter = "a";
        String known = "qw";
        final boolean result = originalActivity.checkNewLetter(newLetter, known);
        final boolean expected = true;
        assertEquals(result,expected);
    }

    @Test
    public void testCheckNewLetter1() throws Exception {
        String newLetter = "asd";
        String known = "qswd";
        final boolean result = originalActivity.checkNewLetter(newLetter,known);
        final boolean expected = true;
        assertEquals(result,expected);
    }

    @Test
    public void testCheckNewLetter2() throws Exception {
        String newLetter = "";
        String known = "qw";
        final boolean result = originalActivity.checkNewLetter(newLetter,known);
        final boolean expected = false;
        assertEquals(result,expected);
    }

    @Test
    public void testLoseCondition() throws Exception {
        int triesRemaining = -1123;
        final boolean result = originalActivity.loseCondition(triesRemaining);
        final boolean expected = false;
        assertEquals(result,expected);
    }

    @Test
    public void testLoseCondition1() throws Exception {
        int triesRemaining = 999999;
        final boolean result = originalActivity.loseCondition(triesRemaining);
        final boolean expected = true;
        assertEquals(result,expected);
    }
}