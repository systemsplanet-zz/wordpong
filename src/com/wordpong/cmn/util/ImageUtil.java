package com.wordpong.cmn.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ImageUtil {
    public static final String IMAGE_DEFAULT = "https://wordpong.appspot.com/i/p/u.png";

    static public String getPictureUrl(String email) {
        String result = IMAGE_DEFAULT;
        if (email != null) {
            String e = email.toLowerCase().trim();
            try {
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.update(e.getBytes(), 0, e.length());
                String hash = new BigInteger(1, m.digest()).toString(16);
                result = "http://www.gravatar.com/avatar/" + hash + "?d=" + IMAGE_DEFAULT;
            } catch (NoSuchAlgorithmException e1) {
            }
        }
        return result;
    }
}
