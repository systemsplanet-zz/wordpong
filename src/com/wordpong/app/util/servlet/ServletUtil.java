package com.wordpong.app.util.servlet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletUtil {
    private static final Logger log = Logger.getLogger(ServletUtil.class.getName());

    static public boolean isSecure(HttpServletRequest req) {
        return req.getScheme().equals("https");
    }

    public static void sessionSet(HttpServletRequest req, String key, Object value) {
        if (req != null && key != null) {
            try {
                HttpSession s = req.getSession();
                if (value == null) {
                    s.removeAttribute(key);
                } else {
                    s.setAttribute(key, value);
                }
                log.info("sessionSet:" + s.getId() + " key:" + key + " val:" + value);
            } catch (Exception e) {
                log.warning("sessionSet: cant set key:[" + key + "] val:[" + value + "] err:" + e.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T sessionGet(HttpServletRequest req, String key, T defaultValue) {
        T value = null;
        if (req != null) {
            try {
                HttpSession s = req.getSession();
                value = (T) s.getAttribute(key);
                if (value == null) {
                    log.info("sessionGet:" + s.getId() + " key:[" + key + "] not found");
                    value = defaultValue;
                    if (value != null) {
                        sessionSet(req, key, value);
                    }
                }
            } catch (Exception e) {
                log.warning("sessionGet: cant get key:[" + key + "] err:" + e.getMessage());
            }
        }
        return value;
    }

    public static Map<String, List<String>> parseUrlQueryString(String url) {
        String query = null;
        if (url != null) {
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                query = urlParts[1];
            }
        }
        return parseQueryString(query);
    }

    // convert a ?x=1&y=b query string to a map
    public static Map<String, List<String>> parseQueryString(String query) {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                String key;
                try {
                    key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = URLDecoder.decode(pair[1], "UTF-8");
                    List<String> values = result.get(key);
                    if (values == null) {
                        values = new ArrayList<String>();
                        result.put(key, values);
                    }
                    values.add(value);
                } catch (UnsupportedEncodingException e) {
                }
            }
        }
        return result;
    }

    // return null if empty map
    public static String mapToQueryString(Map<String, List<String>> m) {
        StringBuffer sb = new StringBuffer();
        if (m != null) {
            sb.append("");
            Set<String> keys = m.keySet();
            for (String k : keys) {
                List<String> ss = m.get(k);
                if (ss != null) {
                    for (String s : ss) {
                        sb.append("k");
                        sb.append("=");
                        sb.append(s);
                        sb.append("&");
                    }
                }
            }
        }
        String result = sb.toString();
        if (result.length() == 0) {
            result = null;
        }
        return result;
    }
}
