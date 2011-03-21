package com.wordpong.app.action;

import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.EmailMessage;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.auth.RememberMe;
import com.wordpong.app.msg.MailUtil;
import com.wordpong.app.stripes.AppActionBeanContext;

public class ForgotPasswordActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/_forgotPassword.jsp";
    private static final Logger log = Logger.getLogger(ForgotPasswordActionBean.class.getName());

    SvcUser svcUser;

    @Validate(required = true, converter = EmailTypeConverter.class, minlength = 4, maxlength = 50)
    private String email;

    public ForgotPasswordActionBean() {
        svcUser = SvcUserFactory.getUserService();
    }

    @DontValidate
    public Resolution back() {
        return new RedirectResolution(LoginActionBean.class);
    }

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(VIEW);
    }

    @HandlesEvent("submit")
    public Resolution submit() {
        Resolution result = new ForwardResolution(VIEW);
        try { 
            String id = svcUser.createPasswordChangeRequest(email);
            String msg = "Use this code to reset your WordPong password: " + id;
            MailUtil.sendAdminMail(new EmailMessage("WordPong Password Reset Code", msg, email, email));
            AppActionBeanContext c = getContext();
            RememberMe.saveEmailToCookie(c.getRequest(), c.getResponse(), email);
            result = new RedirectResolution(ForgotPasswordChangeActionBean.class);
        } catch (Exception e) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("forgotPassword.unableToCreateRequest"));
        }
        return result;
    }

    @ValidationMethod
    public void validateEmail(ValidationErrors errors) {
        try {
            // make sure email exists
            svcUser.findByEmail(email);
        } catch (WPServiceException e) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("forgotPassword.emailNotFound"));
            log.fine("email not found:" + e.getMessage());
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
}
