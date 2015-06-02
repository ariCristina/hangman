package donthang.hangman;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sergiu on 6/2/2015.
 */
public class CreateChallengeActivityTest extends ActivityInstrumentationTestCase2<CreateChallengeActivity> {
    private CreateChallengeActivity originalActivity;

    public CreateChallengeActivityTest() {
        super(CreateChallengeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        originalActivity = getActivity();
    }

    @Test
    public void testCheckStrings() throws Exception {
        String user_id = "12313";
        String auth_token = "";
        String comes_from = "adad";
        String goes_to = "1";

        final boolean result = originalActivity.checkStrings(user_id,auth_token,comes_from,goes_to);
        final boolean expected = false;
        assertEquals(result,expected);
    }
}