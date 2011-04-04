package com.wordpong.api.pojo.locale;

import java.io.Serializable;
import java.util.Locale;

public final class DisplayedLocale implements Serializable {

    private static final long serialVersionUID = 1L;

    private Locale locale;

    /**
     * the locale, in its own language
     */
    private String displayedName;

    public DisplayedLocale(Locale locale) {
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