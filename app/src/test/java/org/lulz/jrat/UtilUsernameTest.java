package org.lulz.jrat;

import org.junit.Test;
import org.junit.Before;
import org.lulz.jrat.Util;

import static org.junit.Assert.assertEquals;

/**
 * Created by excal on 2017-11-08.
 */

public class UtilUsernameTest {

    private String[] usernames;

    @Test
    public void testIsUsernameValid() {
        usernames[1] = "HelloWorld";
        usernames[2] = "Demacia@";
        usernames[3] = "1234";
        usernames[4] = "Hello World";
        usernames[5] = "TimeIsRunningOut";
        boolean nullcase = Util.isUsernameValid(usernames[0]);
        boolean validcase = Util.isUsernameValid(usernames[1]);
        boolean symbolcase = Util.isUsernameValid(usernames[2]);
        boolean numbercase = Util.isUsernameValid(usernames[3]);
        boolean spacecase = Util.isUsernameValid(usernames[4]);
        boolean lengthcase = Util.isUsernameValid(usernames[5]);

        assertEquals(nullcase, false);
        assertEquals(validcase, true);
        assertEquals(symbolcase, false);
        assertEquals(numbercase, true);
        assertEquals(spacecase, false);
        assertEquals(lengthcase, false);
    }
}
