package com.wordpong.app.action;

import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.EmailMessage;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.action.game.GameActionBean;
import com.wordpong.app.auth.RememberMe;
import com.wordpong.app.msg.MailUtil;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.app.stripes.converter.PasswordTypeConverter;
import com.wordpong.cmn.util.debug.LogUtil;

public class RegisterActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final Logger log = Logger.getLogger(RegisterActionBean.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/_register.jsp";

    private User user;

    @Validate(required = true, minlength = 2, maxlength = 20)
    private String firstName;

    @Validate(required = true, minlength = 2, maxlength = 30)
    private String lastName;

    @Validate(required = true, converter = EmailTypeConverter.class, minlength = 4, maxlength = 50)
    private String email;

    @Validate(required = true, converter = PasswordTypeConverter.class, minlength = 4, maxlength = 20)
    private String password;

    public RegisterActionBean() {
    }

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(VIEW);
    }

    // main form processor
    // if called directly, validation events are fired
    // called to register a new user
    public Resolution process() {
        AppActionBeanContext c = getContext();
        Resolution resolution = new ForwardResolution(VIEW);
        if (c != null) {
            user = new User();
            user.setLastName(lastName);
            user.setFirstName(firstName);
            user.setEmail(email);
            user.setPassword(password);
            RememberMe.saveEmailToCookie(c.getRequest(), c.getResponse(), email);
            c.putUserToRequestAndSession(user);
            SvcUser svcUser = SvcUserFactory.getUserService();
            try {
                user = svcUser.save(user);
                String msg = getMsg("register.email.message", new Object[] { user.getFirstName(), user.getEmail() });
                String sub = getMsg("register.email.subject", new Object[] { user.getFirstName()});
                MailUtil.sendAdminMail(new EmailMessage(sub, msg, email, user.getFullName()));
                resolution = new ForwardResolution(GameActionBean.class);
            } catch (Exception e) {
                String reason = "register.unableToAddUser";
                LogUtil.logException(reason, e);
                addGlobalActionError(reason);
                resolution = new ForwardResolution(VIEW);
            }
        }
        return resolution;
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        SvcUser svcUser = SvcUserFactory.getUserService();
        try {
            user = svcUser.findByEmail(email);
            log.info("find email:" + email + " returned:" + user);
            addGlobalActionError("register.duplicateUser");
        } catch (WPServiceException e) {
            // good if not found
        }
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}