package com.wordpong.app.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Role;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.action.admin.AdminActionBean;
import com.wordpong.app.action.game.GameActionBean;
import com.wordpong.app.auth.RememberMe;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.app.stripes.converter.PasswordTypeConverter;

public class LoginActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/_login.jsp";

    private User user;

    @Validate(required = true, converter = EmailTypeConverter.class, minlength = 4, maxlength = 50)
    private String email;

    @Validate(required = true, converter = PasswordTypeConverter.class, maxlength = 20)
    private String password;

    public LoginActionBean() {
    }

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        AppActionBeanContext c = getContext();
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
        // redirect back here
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
                // redirect back here to login
                // return new RedirectResolution(LoginActionBean.class);
                if (c.hasRole(Role.ADMIN)) {
                    resolution = new ForwardResolution(AdminActionBean.class);
                } else if (c.isAuthenticated()) {
                    resolution = new ForwardResolution(GameActionBean.class);
                }
            }
        }
        return resolution;
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        SvcUser svcUser = SvcUserFactory.getUserService();
        if (email != null) {
            try {
                user = svcUser.findByEmail(email);
                if (!user.getPassword().equals(password)) {
                    user = null;
                    getContext().getValidationErrors().addGlobalError(new LocalizableError("passwordIncorrect"));
                }
            } catch (WPServiceException e) {
                getContext().getValidationErrors().addGlobalError(new LocalizableError("emailNotFound"));
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
        email = e;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        password = p;
    }
}