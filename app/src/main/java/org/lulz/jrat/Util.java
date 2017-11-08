package org.lulz.jrat;


/**
 * Util class that checks for ID, PW, email validity
 */
public class Util {

    private static final char[] symbols = {'~', '`', '@', '!', '#', '$', '%',
            '^', '&', '*', '*', '(', ')', '-', '=', '+', '[', ']', '{', '}',
            ',', '.', ';', ' ', '"', ':', '<', '>', '/', '?', '|', '_'};

    /**
     * check for validity of email address
     * @param username username to be checked
     * @return true if valid
     */
    public static boolean isUsernameValid(String username) {

        boolean validity = true;

        if (username == null) {
            return false;
        }

        // check if the username contains any special symbol
        for (int i = 0; i < symbols.length; i++) {
            if (username.contains(Character.toString(symbols[i])) ) {
                validity = false;
            }
        }

        if (username.length() < 5 || username.length() > 10) {
            validity = false;
        }
        return validity;
    }

    /**
     * check for validity of password
     * @param password password user entered during registration
     * @return true if valid
     */
    public boolean isPasswordValid(String password) {

        boolean validity = true;

        if (password == null) {
            return false;
        }

        // check if the password contains any special symbol
        for (int i = 0; i < symbols.length; i++) {
            if (password.contains(Character.toString(symbols[i])) ) {
                validity = false;
            }
        }

        if (password.length() < 5
                || password.length() > 10) {
            validity = false;
        }

        if (password.equals("1234")) {
            validity = false;
        }
        return validity;
    }

    /**
     * check for validity of email address
     * @param email email user entered during registration
     * @return true if valid
     */
    public boolean isEmailValid(String email) {
        boolean validity = true;

        if (email == null) {
            return false;
        }

        if (!email.contains("@") || email.contains(" ")) {
            validity = false;
        }
        return validity;
    }
}
