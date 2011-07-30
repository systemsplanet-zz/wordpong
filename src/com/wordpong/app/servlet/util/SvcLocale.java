package com.wordpong.app.servlet.util;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Configure all services for the users Locale
 *
 */
public class SvcLocale {
    private static final ThreadLocal<SvcLocale> INSTANCE = new ThreadLocal<SvcLocale>();
    private Locale locale = Locale.getDefault();
    private ResourceBundle bundle = null;
    private String encoding;
    private SvcLocale() {
    }

    public static void initServiceThread(Locale l, ResourceBundle rb, String encoding) {
        SvcLocale sl = new SvcLocale();
        sl.setLocale(l);
        sl.setBundle(rb);
        sl.setEncoding(encoding);
        INSTANCE.set(sl);
    }

    public static String get(String key) {
        SvcLocale sl = INSTANCE.get();
        String result = "?" + key + "?";
        ResourceBundle rb = sl.getBundle();
        if (rb != null && rb.containsKey(key)) {
            Object val = rb.getObject(key);
            if (val instanceof String) {
                result = (String) val;
            }
        }
        return result;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public Locale getLocale() {
        return locale;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    
}
