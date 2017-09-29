package org.lulz.jrat;

import java.util.HashMap;

/**
 * Authentication interface that maps User objects
 * to userID as key
 * Jin Woo Lee 09/27
 */
public interface Authentication {
    static final HashMap<String, User> authMap = new HashMap<>();
}
