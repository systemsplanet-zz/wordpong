package com.wordpong.app.action;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.admin.AdminActionBean;
import com.wordpong.app.action.game.GameActionBean;
import com.wordpong.app.servlet.util.RememberMe;
import com.wordpong.app.servlet.util.ServletUtil;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.app.stripes.AppLocalePicker;
import com.wordpong.app.stripes.converter.PasswordTypeConverter;
import com.wordpong.util.secure.Role;

public class LoginActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/_login.jsp";

    private User user;

    @Validate(required = true, converter = EmailTypeConverter.class, minlength = 4, maxlength = 50)
    private String email;

    @Validate(required = true, converter = PasswordTypeConverter.class, maxlength = 20)
    private String password;

    private String queryString;

    private SvcUser svcUser;

    public LoginActionBean() {
        svcUser = SvcUserFactory.getSvcUser();
    }

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        // if there is a query string, just save in the session and we're done.
        if (queryString != null) {
            setSessionAttribute(SESS_QUERY_STRING, queryString);
            return new ForwardResolution(VIEW); // return from save qs
        }

        // if register is in session then redirect to registration page
        String qs = (String) getSessionAttribute(SESS_QUERY_STRING);
        if (qs != null) {
            Map<String, List<String>> m = ServletUtil.parseQueryString(qs);
            List<String> rs = m.get(QUERY_PARAM_REGISTER);
            if (rs != null && rs.size() == 1) {
                String registerUser = rs.get(0);
                if (registerUser != null) {
                    try {
                        svcUser.findByEmail(registerUser);
                    } catch (WPServiceException e) {
                        // if new user, switch to registration page
                        return new ForwardResolution(RegisterActionBean.class);
                    }
                }
            }
        }

        AppActionBeanContext c = getContext();
        if (user == null) {
            user = c.getUserFromSession();
        }

        if (email == null) {
            email = RememberMe.getEmailFromCookie(c.getRequest(), c.getResponse());
        }
        if (password == null) {
            password = RememberMe.getPasswordFromCookie(c.getRequest(), c.getResponse());
        }
        if (email != null && password != null) {
            return process();
        }
        return new ForwardResolution(VIEW);
    }
    

    @DontValidate
    @HandlesEvent("logout")
    public Resolution logout() {
        AppActionBeanContext c = getContext();
        if (c != null) {
            email = RememberMe.getEmailFromCookie(c.getRequest(), c.getResponse());
            password = null;
            user = null;
            RememberMe.savePasswordToCookie(c.getRequest(), c.getResponse(), null);
            c.putUserToRequestAndSession(null);
        }
        return new ForwardResolution(VIEW);
    }

    // main form processor
    // if called directly, validation events are fired
    public Resolution process() {
        AppActionBeanContext c = getContext();
        Resolution resolution = new ForwardResolution(VIEW);
        if (c != null) {
            if (user != null) {
                c.putUserToRequestAndSession(user);
            } else {
                user = c.getUserFromSession();
            }
            // remember me login?
            if (user == null && c.getEventName().equalsIgnoreCase("logout") == false) {
                validateUser(getContext().getValidationErrors());
                if (user != null) {
                    c.putUserToRequestAndSession(user);
                }
            }
            if (user != null) {
                RememberMe.saveEmailToCookie(c.getRequest(), c.getResponse(), user.getEmail());
                RememberMe.savePasswordToCookie(c.getRequest(), c.getResponse(), user.getPassword());
                Locale l = user.getLocale();
                if (l != null) {
                    AppLocalePicker.setPreferredLocale(getRequest(), l);
                }
                if (c.hasRole(Role.ADMIN)) {
                    resolution = new RedirectResolution(AdminActionBean.class);
                } else if (c.isAuthenticated()) {
                    resolution = new RedirectResolution(GameActionBean.class);
                }
            }
        }
        return resolution;
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        if (email != null) {
            try {
                user = svcUser.findByEmail(email);
                if (!user.getPassword().equals(password)) {
                    user = null;
                    addGlobalActionError("login.passwordIncorrect");
                }
            } catch (WPServiceException e) {
                addGlobalActionError("login.emailNotFound");
            }
        }
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String e) {
        if (e != null) {
            e = e.trim().toLowerCase();
        }
        email = e;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        if (p != null) {
            p = p.trim();
        }
         password = p;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

}