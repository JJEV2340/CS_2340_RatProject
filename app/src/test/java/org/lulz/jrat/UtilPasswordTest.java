package org.lulz.jrat;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.lulz.jrat.Util.isPasswordValid;

/**
 * Created by excal on 2017-11-08.
 */

public class UtilPasswordTest {

    private final char[] symbols = {'~', '`', '@', '!', '#', '$', '%',
                '^', '&', '*', '*', '(', ')', '-', '=', '+', '[', ']', '{', '}',
                ',', '.', ';', ' ', '"', ':', '<', '>', '/', '?', '|', '_'};

    public static final int TIMEOUT = 200;

    @Test(timeout = TIMEOUT)
    public void passwordValidTest() {
        String str = "";
        //test for empty
        assertFalse(isPasswordValid(str));

        //test for password length < 5
        for(int i = 0; i < 4; i++) {
                str = str + "t";
                assertFalse(isPasswordValid(str));
            }

            //test for passwords that should be okay
            for(int i = 0; i< 6; i++) {
                str = str + "w";
                assertTrue(isPasswordValid(str));
            }
            //tests for password length > 10
            for(int i = 0; i< 25; i++) {
                str = str + "t";
                assertFalse(isPasswordValid(str));
            }
            //tests for symbols
            for(int i = 0; i < symbols.length; i++) {
                str = "" + symbols[i];
                assertFalse(isPasswordValid(str));
            }

            //tests for "1234"
            str = "1234";
            assertFalse(isPasswordValid(str));
        }
}
