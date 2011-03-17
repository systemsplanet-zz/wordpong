package com.wordpong.api.model;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Preferences of an account (immutable value object)
 */
// @Immutable
public class Preferences implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The preferred locale (nullable)
	 */
	private final Locale locale;

	/**
	 * The preferred time zone (not nullable)
	 */
	private final TimeZone timeZone;

	/**
	 * Constructor
	 *
	 * @param locale
	 *            the preferred locale (nullable)
	 * @param timeZone
	 *            the preferred time zone (not nullable)
	 * @param passwordsUnmasked
	 *            flag indicating if passwords must be unmasked by default
	 * @param passwordGenerationPreferences
	 *            the password generation preferences
	 */
	public Preferences(Locale locale, TimeZone timeZone) {
		if (timeZone == null) {
			throw new IllegalArgumentException("The time zone may not be null");
		}
		this.locale = locale;
		this.timeZone = timeZone;

	}

	/**
	 * Gets the preferred locale
	 *
	 * @return the preferred locale, or <code>null</code> if there is none
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Gets the preferred time zone
	 *
	 * @return the preferred time zone, or <code>null</code> if there is none
	 */
	public TimeZone getTimeZone() {
		return timeZone;
	}

	/**
	 * Creates a copy of this object with a different locale
	 *
	 * @param locale
	 *            the different locale (nullable)
	 * @return the copy of this instance, with the given locale
	 */
	public Preferences withLocale(Locale locale) {
		return new Preferences(locale, this.timeZone);
	}

	/**
	 * Creates a copy of this object with a different time zone
	 *
	 * @param timeZone
	 *            the different time zone (not nullable)
	 * @return the copy of this instance, with the given time zone
	 */
	public Preferences withTimeZone(TimeZone timeZone) {
		return new Preferences(this.locale, timeZone);
	}

	/**
	 * Creates a copy of this object with a different preferences regarding
	 * password masking
	 *
	 * @param passwordsUnmasked
	 *            the different flag
	 * @return the copy of this instance, with the given preference
	 */
	public Preferences withPasswordsUnmasked(boolean passwordsUnmasked) {
		return new Preferences(this.locale, this.timeZone);
	}
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
