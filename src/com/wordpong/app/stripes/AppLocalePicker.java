package com.wordpong.app.stripes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.localization.DefaultLocalePicker;

import com.wordpong.app.servlet.util.SvcLocale;

/**
 * The locale picker usd by MemWords. Rather than using a list of locales and
 * encodings found in the web.xml file, it used a hard-coded list. The locale
 * picking algorithm is the same as the default one, except it first looks up
 * into the session, in order for the user to explicitely choose another locale
 * via the application, among the hard-coded list of supported locales.
 */

public class AppLocalePicker extends DefaultLocalePicker {
    private static final Logger log = Logger.getLogger(AppLocalePicker.class.getName());

	/**
	 * The hard-coded, read-only list of supported locales
	 */
	public static final List<Locale> SUPPORTED_LOCALES;
    static public final Locale SPANISH = new Locale("es", "ES");

	/**
	 * The map of encodings by locale
	 */
	private static final Map<Locale, String> ENCODINGS;

	/**
	 * The session attribute where the preferred locale is stored
	 */
	private static final String SESSION_LOCALE = "com.wordpoing.session.locale";

	static {
		List<Locale> locales = new ArrayList<Locale>();
		locales.add(Locale.US);
		//locales.add(Locale.CHINA);
		locales.add(SPANISH);
		locales.add(Locale.FRANCE);

		SUPPORTED_LOCALES = Collections.unmodifiableList(locales);

		ENCODINGS = Collections.emptyMap();
	}

	@Override
	public void init(Configuration configuration) {
		this.configuration = configuration;
		this.locales = SUPPORTED_LOCALES;
		this.encodings = ENCODINGS;
	}
	
	@Override
	public Locale pickLocale(HttpServletRequest request) {
		Locale locale = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			Locale preferredLocale = (Locale) session
					.getAttribute(SESSION_LOCALE);
			if (preferredLocale != null) {
				locale = preferredLocale;
			}
		}
		if (locale == null) {
			locale = super.pickLocale(request);
		}
		// configure all services for the current locale
        try {
            ResourceBundle bundle = StripesFilter.getConfiguration().getLocalizationBundleFactory().getErrorMessageBundle(locale);
            String encoding = request.getCharacterEncoding();
            SvcLocale.initServiceThread(locale, bundle, encoding);
        } catch (MissingResourceException mre) {
            log.warning("could not find the error messages resource bundle. " + "Check that you have a StripesResources.properties in your classpath");
        }
		return locale;
	}

	/**
	 * Sets the preferred locale in the session, so that it's picked up by this
	 * locale picker for subsequent requests
	 * 
	 * @param request
	 *            the current request
	 * @param preferredLocale
	 *            the preferred locale
	 */
	public static void setPreferredLocale(HttpServletRequest request,
			Locale preferredLocale) {
		HttpSession session = request.getSession(true);
		session.setAttribute(SESSION_LOCALE,
				preferredLocale);
	}
}
