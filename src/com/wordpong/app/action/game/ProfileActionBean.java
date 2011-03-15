package com.wordpong.app.action.game;

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
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.auth.RememberMe;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.app.stripes.converter.PasswordTypeConverter;

public class ProfileActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/game/_profile.jsp";

    @Validate(required = true, converter = EmailTypeConverter.class, minlength = 4, maxlength = 50)
    private String email;

    @Validate(required = true, converter = PasswordTypeConverter.class, maxlength = 20)
    private String password;

    @Validate(required = true, maxlength = 50)
    private String firstName;

    @Validate(required = true, maxlength = 100)
    private String lastName;

    @Validate(required = true, maxlength = 200)
    private String pictureUrl;

    public ProfileActionBean() {
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
            // return process();
        }
        return new ForwardResolution(VIEW);
    }

    @DontValidate
    @HandlesEvent("save")
    public Resolution save() {
        AppActionBeanContext c = getContext();
        if (c != null) {
            // c.putUserToRequestAndSession(null);
        }
        // redirect back here
        return new ForwardResolution(VIEW);
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        SvcUser svcUser = SvcUserFactory.getUserService();
        if (email != null) {
            try {
            	//TODO: make sure new email is unique
                User user = svcUser.findByEmail(email);
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

	public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

}