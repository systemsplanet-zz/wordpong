package com.wordpong.app.action.apiv1.pojo;

import java.util.HashMap;

import com.google.gson.Gson;

public class ApiResult extends HashMap<String, String> {
    private static final long serialVersionUID = 1L;
    public static ApiResult ERR500_INVALID_ARGUMENT = new ApiResult();
    public static ApiResult ERR501_INVALID_PASSWORD = new ApiResult();
    public static ApiResult ERR000_SUCCESS = new ApiResult();
    public static ApiResult ERR502_INVALID_USER_ID = new ApiResult();
    public static ApiResult ERR503_LOGIN_REQUIRED = new ApiResult();
    public static ApiResult ERR504_GENERAL_ERROR = new ApiResult();
    public static ApiResult ERR505_INCOMPLETE = new ApiResult();

    public static String KEY_ERROR = "error";
    public static String KEY_MESSAGE = "message";
    public static String KEY_DETAIL = "detail";

    static {
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
        ERR505_INCOMPLETE.put(KEY_ERROR, "505");
        ERR505_INCOMPLETE.put(KEY_MESSAGE, "Please complete all fields");
    }

    public static ApiResult addMessage(ApiResult err, String detail) {
        ApiResult result = new ApiResult();
        result.put(KEY_DETAIL, detail);
        result.put(KEY_ERROR, err.get(KEY_ERROR));
        result.put(KEY_MESSAGE, err.get(KEY_MESSAGE));
        return result;
    }
    public String toString() {
        Gson gson = new Gson();
        String result = gson.toJson(this);
        return result;
    }
}
