package com.wordpong.app.servlet.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles cookies from different hosts domains by prefixing the HOST name
 * http://www.javaworld.com/javaworld/jw-01-2001/jw-0126-servlets.html?page=3
 * 
 */
public class CookieUtil {
	private static final String delim = "/";

	CookieUtil() {
	}

	private static String getPrefix(HttpServletRequest request) {
		if (null != request) {
			String tmp = request.getHeader("HOST");
			if (null != tmp) {
				return (tmp + delim);
			}
		}
		return ("unknownhost" + delim);
	}

	public static void addCookie(HttpServletRequest request,
			HttpServletResponse response, Cookie cookie) {
		if (null != request) {
			if (null != cookie) {
				String newName = getPrefix(request) + cookie.getName();
				Cookie newCookie = new Cookie(newName, cookie.getValue());
				if (cookie.getValue() == null) {
					newCookie.setMaxAge(0);
				} else {
					newCookie.setMaxAge(cookie.getMaxAge());
				}
				response.addCookie(newCookie);
			}
		}
	}

	// get 1st cookie with a given name
	public static Cookie getCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		Cookie result = null;
		if (name != null) {
			Cookie[] allCookies = getAllCookies(request, response);
			for (Cookie c : allCookies) {
				if (name.equals(c.getName())) {
					result = c;
					break;
				}
			}
		}
		return result;
	}

	public static Cookie[] getAllCookies(HttpServletRequest request,
			HttpServletResponse response) {
		List<Cookie> list = new ArrayList<Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			String prefix = getPrefix(request);
			for (int x = 0; x < cookies.length; x++) {
				if ((null != cookies[x])
						&& (cookies[x].getName().startsWith(prefix))) {
					String name = cookies[x].getName();
					String value = cookies[x].getValue();
					String newName = name.substring(prefix.length(), name
							.length());
					Cookie warped = new Cookie(newName, value);
					warped.setMaxAge(cookies[x].getMaxAge());
					list.add(warped);
				} else {
					// skip it (sub- or super- domain cookie bug)
				}
			}
		}
		Cookie[] result = list.toArray(new Cookie[0]);
		return result;
	}
}
