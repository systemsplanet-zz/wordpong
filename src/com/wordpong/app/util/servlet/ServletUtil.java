package com.wordpong.app.util.servlet;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletUtil {
	private static final Logger log = Logger.getLogger(ServletUtil.class
			.getName());

	static public boolean isSecure(HttpServletRequest req) {
		return req.getScheme().equals("https");
	}

	public static void sessionSet(HttpServletRequest req, String key,
			Object value) {
		if (req != null && key != null) {
			try {
				HttpSession s = req.getSession();
				if (value == null) {
					s.removeAttribute(key);
				} else {
					s.setAttribute(key, value);
				}
				log.info("sessionSet:" + s.getId() + " key:" + key + " val:"
						+ value);
			} catch (Exception e) {
				log.warning("sessionSet: cant set key:[" + key + "] val:[" + value
						+ "] err:" + e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T sessionGet(HttpServletRequest req, String key,
			T defaultValue) {
		T value = null;
		if (req != null) {
			try {
				HttpSession s = req.getSession();
				value = (T) s.getAttribute(key);
				if (value == null) {
					log.info("sessionGet:"+s.getId()+" key:[" + key + "] not found"
							);
					value = defaultValue;
					if (value != null) {
						sessionSet(req, key, value);
					}
				}
			} catch (Exception e) {
				log.warning("sessionGet: cant get key:[" + key + "] err:"
						+ e.getMessage());
			}
		}
		return value;
	}

}
