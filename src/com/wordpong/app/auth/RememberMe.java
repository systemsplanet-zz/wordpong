package com.wordpong.app.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.util.servlet.CookieUtil;

public class RememberMe {
	private static String COOKIE_EMAIL = "c.email";
	private static String COOKIE_PASSWORD = "c.pwd";
	private static int TEN_YEARS = 60 * 60 * 24 * 30 * 12 * 10;

	public static void saveEmailToCookie(HttpServletRequest request,
			HttpServletResponse response, String email) {
		saveToCookie(request, response, COOKIE_EMAIL, email);
	}

	public static void savePasswordToCookie(HttpServletRequest request,
			HttpServletResponse response, String pwd) {
		saveToCookie(request, response, COOKIE_PASSWORD, pwd);
	}

	public static void removePasswordFromCookie(HttpServletRequest request,
			HttpServletResponse response, String pwd) {
		saveToCookie(request, response, COOKIE_PASSWORD, null);
	}

	public static void saveToCookie(HttpServletRequest request,
			HttpServletResponse response, String key, String email) {
		Cookie cookie = new Cookie(key, email);
		cookie.setMaxAge(TEN_YEARS);
		CookieUtil.addCookie(request, response, cookie);
	}

	public static String getEmailFromCookie(HttpServletRequest request,
			HttpServletResponse response) {
		String result = getFromCookie(request, response, COOKIE_EMAIL);
		return result;
	}

	public static String getPasswordFromCookie(HttpServletRequest request,
			HttpServletResponse response) {
		String result = getFromCookie(request, response, COOKIE_PASSWORD);
		return result;
	}

	public static String getFromCookie(HttpServletRequest request,
			HttpServletResponse response, String key) {
		Cookie cookie = CookieUtil.getCookie(request, response, key);
		String result = null;
		if (cookie != null) {
			result = cookie.getValue();
		}
		return result;
	}

	public static User getUserFromCookie(HttpServletRequest request,
			HttpServletResponse response) {
		User result = null;
		String email = getEmailFromCookie(request, response);
		if (email != null) {
			try {
				result = SvcUserFactory.getSvcUser().findByEmail(email);
			} catch (WPServiceException e) {
			}
		}
		return result;
	}
}
