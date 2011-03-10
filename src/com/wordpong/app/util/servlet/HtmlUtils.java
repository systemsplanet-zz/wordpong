package com.wordpong.app.util.servlet;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang.StringEscapeUtils;

import com.wordpong.api.err.WPExceptionRuntime;



/**
 * Utility class for HTML formatting
 * @author JB
 */
public final class HtmlUtils {
    /**
     * Constructor. Private, all static methods
     */
    private HtmlUtils() {
    }

    /**
     * Transforms a plain multi-line string into an HTML multi-line String (where newline
     * characters are replaced with &lt;br/&gt; tags). This tag also xml-escapes the lines
     * if <code>escapeXml</code> is true
     * @param s the string to transform
     * @param escapeXml <code>true</code> if the lines must be xml-escaped
     * @return the transformed string, or an empty string if <code>s</code> is <code>null</code>.
     */
    public static String nlToBr(String s, boolean escapeXml) {
        if (s == null) {
            return "";
        }
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new StringReader(s));
            boolean firstLine = true;
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (!firstLine) {
                    builder.append("<br/>");
                }
                builder.append(StringEscapeUtils.escapeXml(line));
                firstLine = false;
            }
            return builder.toString();
        }
        catch (IOException e) {
            throw new WPExceptionRuntime(e);
        }
    }
}
