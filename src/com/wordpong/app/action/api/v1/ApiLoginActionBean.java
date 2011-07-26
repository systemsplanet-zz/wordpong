package com.wordpong.app.action.api.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;


import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.action.api.ApiResult;
import com.wordpong.app.util.secure.Encrypt;

@UrlBinding("/rest/v1/login/{email}/{password}")
public class ApiLoginActionBean extends BaseActionBean {
    private static final Logger log = Logger.getLogger(ApiLoginActionBean.class.getName());

    private String email;
    private String password;

    public ApiLoginActionBean() {
    }

    @DefaultHandler
    public Resolution renderJSON()  {
        List<HashMap<String, String>> result   = new ArrayList<HashMap<String,String>>();
        if (email == null || password == null) {
            result.add(ApiResult.ERR500_INVALID_ARGUMENT);
        } else {
            try {
                SvcUser _svcUser = SvcUserFactory.getUserService();
                User user = _svcUser.findByEmail(email);
                password = Encrypt.hashSha1(password);
                if (!user.getPassword().equals(password)) {
                    result.add(ApiResult.ERR501_INVALID_PASSWORD);
                } else {
                    result.add(ApiResult.ERR000_SUCCESS);
                    getContext().putUserToRequestAndSession(user);
                }
            } catch (WPServiceException e) {
                ApiResult err = ApiResult.addMessage(ApiResult.ERR502_INVALID_USER_ID, e.getMessage());
                result.add(err);
            }
        }
        log.info("API login email:" + email + " result:" + result);
        return new StreamingResolution("application/json", result.toString());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}