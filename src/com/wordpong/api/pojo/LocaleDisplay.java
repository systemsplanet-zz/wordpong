package com.wordpong.api.pojo;

import java.io.Serializable;
import java.util.Locale;

public final class LocaleDisplay implements Serializable {

    private static final long serialVersionUID = 1L;

    private Locale locale;

    /**
     * the locale, in its own language
     */
    private String displayedName;

    //upper case 1st char in locale name
    public LocaleDisplay(Locale locale) {
        this.locale = locale;
        this.displayedName = locale.getDisplayName(this.locale);
        if (displayedName != null && displayedName.length() > 1) {
            displayedName = displayedName.substring(0, 1).toUpperCase() + displayedName.substring(1);
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public String getDisplayedName() {
        return displayedName;
    }
}
