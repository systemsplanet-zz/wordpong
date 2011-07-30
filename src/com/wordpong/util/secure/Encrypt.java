package com.wordpong.util.secure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.sourceforge.stripes.util.Base64;

public class Encrypt {
	public static String hashSha1(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] bytes = md.digest(password.getBytes());
			return Base64.encodeBytes(bytes);
		} catch (NoSuchAlgorithmException exc) {
			throw new IllegalArgumentException(exc);
		}
	}
}
