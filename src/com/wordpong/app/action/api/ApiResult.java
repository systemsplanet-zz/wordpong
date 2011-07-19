package com.wordpong.app.action.api;

import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiResult {
    private static final Logger log = Logger.getLogger(ApiResult.class.getName());

    public static JSONObject ERR500_INVALID_ARGUMENT = new JSONObject();
    public static JSONObject ERR501_INVALID_PASSWORD = new JSONObject();
    public static JSONObject ERR000_SUCCESS = new JSONObject();
    public static JSONObject ERR502_INVALID_USER_ID = new JSONObject();
    public static JSONObject ERR503_LOGIN_REQUIRED = new JSONObject();
    public static JSONObject ERR504_GENERAL_ERROR = new JSONObject();

    public static String KEY_ERROR = "error";
    public static String KEY_MESSAGE = "message";
    public static String KEY_DETAIL = "detail";
    
    static {
        try {
            ERR000_SUCCESS.put(KEY_ERROR, "0");
            ERR000_SUCCESS.put(KEY_MESSAGE, "Success");
            ERR500_INVALID_ARGUMENT.put(KEY_ERROR, "500");
            ERR500_INVALID_ARGUMENT.put(KEY_MESSAGE, "Email and password is required on login URL");
            ERR501_INVALID_PASSWORD.put(KEY_ERROR, "501");
            ERR501_INVALID_PASSWORD.put(KEY_MESSAGE, "Password does not match user account");
            ERR502_INVALID_USER_ID.put(KEY_ERROR, "502");
            ERR502_INVALID_USER_ID.put(KEY_MESSAGE, "Email address not found");
            ERR503_LOGIN_REQUIRED.put(KEY_ERROR, "503");
            ERR503_LOGIN_REQUIRED.put(KEY_MESSAGE, "Login required");
            ERR504_GENERAL_ERROR.put(KEY_ERROR, "504");
            ERR504_GENERAL_ERROR.put(KEY_MESSAGE, "General Error");
    
        } catch (JSONException e) {
            log.warning("Unable to initialize API result objects");
            e.printStackTrace();
        }
    }

    public static JSONObject addMessage(JSONObject err, String detail) {
        JSONObject result = new JSONObject();
        try {
            result.put(KEY_DETAIL, detail);
            result.put(KEY_ERROR, err.get(KEY_ERROR));
            result.put(KEY_MESSAGE, err.get(KEY_MESSAGE));
        } catch (JSONException e) {
        }
        return result;
    }
}
