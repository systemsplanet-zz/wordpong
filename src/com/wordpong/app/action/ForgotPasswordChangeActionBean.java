package com.wordpong.app.action;

import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.PasswordChangeRequest;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.servlet.util.RememberMe;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.app.stripes.converter.PasswordTypeConverter;

public class ForgotPasswordChangeActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/_forgotPasswordChange.jsp";
    private static final Logger log = Logger.getLogger(ForgotPasswordChangeActionBean.class.getName());

    SvcUser svcUser;

    @Validate(required = true, minlength = 1)
    private String code;

    @Validate(required = true, converter = PasswordTypeConverter.class, maxlength = 20)
    private String password;

    public ForgotPasswordChangeActionBean() {
        svcUser = SvcUserFactory.getSvcUser();
    }

    @DontValidate
    public Resolution back() {
        return new ForwardResolution(ForgotPasswordActionBean.class);
    }

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(VIEW);
    }

    @HandlesEvent("submit")
    public Resolution submit() {
        AppActionBeanContext c = getContext();
        String email = RememberMe.getEmailFromCookie(c.getRequest(), c.getResponse());
        PasswordChangeRequest pcr = SvcUserFactory.getSvcUser().getPasswordChangeRequest(code, email);
        if (pcr == null) {
            addGlobalActionError("forgotPasswordChange.invalidCode");
        } else {
            if (pcr.getEmail() != null && email != null && email.equalsIgnoreCase(pcr.getEmail()) == false) {
                addGlobalActionError("forgotPasswordChange.invalidCodeEmail");
            } else {
                // Change the password
                User user;
                try {
                    user = svcUser.findByEmail(email);
                    user.setPassword(password);
                    svcUser.save(user);
                    addGlobalActionError("forgotPasswordChange.passwordReset");
                    log.info("password reset:" + user);
                } catch (WPServiceException e) {
                    addGlobalActionError("forgotPasswordChange.cantUpdateUser");
                }
            }
        }
        return new ForwardResolution(VIEW);
    }

    @ValidationMethod
    public void validate(ValidationErrors errors) {
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String c) {
        if (c != null) {
            c = c.trim();
        }
        this.code = c;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        if (p != null) {
            p = p.trim();
        }
        this.password = p;
    }
}
