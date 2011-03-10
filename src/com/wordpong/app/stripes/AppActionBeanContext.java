package com.wordpong.app.stripes;

import java.util.logging.Logger;
import javax.servlet.jsp.jstl.core.Config;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;

import net.sourceforge.stripes.action.ActionBeanContext;

import org.apache.commons.codec.binary.Base64;

import com.wordpong.api.model.Role;
import com.wordpong.api.model.User;
import com.wordpong.app.util.secure.CryptoEngine;
import com.wordpong.app.util.secure.CryptoEngineFactory;
import com.wordpong.app.util.servlet.CookieUtil;
import com.wordpong.app.util.servlet.ServletUtil;
import com.wordpong.cmn.svc.SvcCommon;
import com.wordpong.cmn.svc.SvcCommonFactory;

public class AppActionBeanContext extends ActionBeanContext {
	private static final String SESSION_USER = "s.usr";
	private static final String REQUEST_USER = "r.usr";
	private static final String REQUEST_URL = "r.url";
	private static final String COOKIE_USER = "c.usr";
	private static final String SESSION_PASSWORD = "s.pwd";
	private static final Logger log = Logger
			.getLogger(AppActionBeanContext.class.getName());
	private SvcCommon _svcCommon;
	private User _user;

	public AppActionBeanContext() {
		// log.info("AppActionBeanContext created.");
		_svcCommon = SvcCommonFactory.getCommonService();
	}

	public void putUserToRequestAndSession(User user) {
		_user = user;
		getRequest().setAttribute(REQUEST_USER, user);
		ServletUtil.sessionSet(getRequest(), SESSION_USER, user);
	}

	public User getUserFromSession() {
		if (_user == null) {
			_user = ServletUtil.sessionGet(getRequest(), SESSION_USER, null);
		}
		return _user;
	}

	/**
	 * Gets the user information stored in the request, with secret key
	 *
	 * @return the user information, with secret key
	 */
	public User getUserFromRequest() {
		return (User) getRequest().getAttribute(REQUEST_USER);
	}

	public boolean hasRole(String role) {
		boolean result = false;
		if (_user != null) {
			result = _user.hasRole(role);
		}
		if (result == false && Role.isAdmin(role)) {
			result = _svcCommon.isUserAdmin();
		}
		return result;
	}

	public boolean isAuthenticated() {
		boolean result = false;
		User u = getUserFromSession();
		result = u != null;
		if (result == false) {
			result = _svcCommon.isLoggedIn() && _svcCommon.isUserAdmin();
		}
		return result;
	}

	/**
	 * Method called by the authentication interceptor, when a GET URL is asked
	 * but needs an authentication before. The login action bean then finds the
	 * requested URL in the context and places it in a hidden field
	 *
	 * @param url
	 *            the requested URL
	 */
	public void putUrlToRequest(String url) {
		getRequest().setAttribute(REQUEST_URL, url);
	}

	/**
	 * Method called by the authentication interceptor. It fetches the user
	 * information (without secret key from the session, then (if found) fetches
	 * the secret key from the cookie, and builds a user information with secret
	 * key that it stores in the request.
	 */
	protected void getUserEncryptedInfo() {
		if (getUserFromRequest() != null) {
			return;
		}
		if (_user != null) {
			Cookie secretKeyCookie = CookieUtil.getCookie(getRequest(),
					getResponse(), COOKIE_USER);
			if (secretKeyCookie != null) {
				// important : no end of line, else the cookie contains control
				// characters,
				// and it doesn't work
				Base64 base64 = new Base64(-1);
				byte[] encryptedSecretKey = base64.decode(secretKeyCookie
						.getValue());

				byte[] cookieEncryptionKeyAsBytes = (byte[]) getRequest()
						.getSession().getAttribute(SESSION_PASSWORD);
				CryptoEngine cryptoEngine = CryptoEngineFactory
						.getCryptoEngine();
				SecretKey cookieEncryptionKey = cryptoEngine
						.bytesToSecretKey(cookieEncryptionKeyAsBytes);
				byte[] secretKeyAsBytes = cryptoEngine
						.decrypt(
								encryptedSecretKey,
								cookieEncryptionKey,
								cryptoEngine
										.buildInitializationVector(cookieEncryptionKeyAsBytes));
				SecretKey secretKey = cryptoEngine
						.bytesToSecretKey(secretKeyAsBytes);
				_user.setEncryptionKey(secretKey);
				putUserInfoToRequestAndSession(_user);
				log.fine("save encrypted user info");
			}
		}
	}

	/**
	 * Sets the user information (after a login or a change in the preferences).
	 * It stores the user information with secret key in the request, the user
	 * information without secret key in the session, the locale in the session
	 * where the locale picker can find it, and the time zone in the session
	 * where the JSTL tags can find it
	 *
	 * @param user
	 *            the new user information, with secret key
	 */
	public void putUserInfoToRequestAndSession(User user) {
		AppLocalePicker.setPreferredLocale(getRequest(), user.getPreferences()
				.getLocale());
		putUserToRequestAndSession(user);
		Config.set(getRequest().getSession(), Config.FMT_TIME_ZONE, user
				.getPreferences().getTimeZone());
	}
}
