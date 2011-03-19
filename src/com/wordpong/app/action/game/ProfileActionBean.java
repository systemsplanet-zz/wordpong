package com.wordpong.app.action.game;

import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import com.wordpong.app.stripes.converter.ImageUrlTypeConverter;
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
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.app.util.secure.Encrypt;

public class ProfileActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final Logger log = Logger.getLogger(ProfileActionBean.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/game/_profile.jsp";

    private SvcUser svcUser;

    private User user;

    @Validate(required = true, converter = EmailTypeConverter.class, minlength = 4, maxlength = 50)
    private String email;

    @Validate(required = true, maxlength = 20, minlength = 4)
    private String password;

    @Validate(required = true, maxlength = 50, minlength = 2)
    private String firstName;

    @Validate(required = true, maxlength = 100, minlength = 2)
    private String lastName;

    @Validate(required = false, converter = ImageUrlTypeConverter.class, maxlength = 200, minlength = 18)
    private String pictureUrl;

    public ProfileActionBean() {
        svcUser = SvcUserFactory.getUserService();
    }

    @DontValidate
    public Resolution back() {
        return new RedirectResolution(GameActionBean.class);
    }

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        AppActionBeanContext c = getContext();
        if (c != null) {
            if (user == null) {
                user = c.getUserFromSession();
                if (user != null) {
                    if (firstName == null) {
                        firstName = user.getFirstName();
                    }
                    if (lastName == null) {
                        lastName = user.getLastName();
                    }
                    if (email == null) {
                        email = user.getEmail();
                    }
                    if (password == null) {
                        password = user.getPassword();
                    }
                    if (pictureUrl == null) {
                        pictureUrl = user.getPictureUrl();
                    }
                } else {
                    // TODO: session expiration? show error popup, redirect home
                }
            }
        }
        return new ForwardResolution(VIEW);
    }

    @DontValidate
    @HandlesEvent("save")
    public Resolution save() {
        AppActionBeanContext c = getContext();
        if (c != null) {
            try {
                if (c.getValidationErrors().size() == 0) {
                    user = c.getUserFromSession();
                    if (user != null) {
                        user.setFirstName(firstName);
                        user.setLastName(lastName);
                        user.setEmail(email);
                        user.setPictureUrl(pictureUrl);
                        String epwd = Encrypt.hashSha1(password);
                        if (user.getPassword() != null && user.getPassword().equals(epwd) == false) {
                            user.setPassword(epwd);
                            password = epwd;
                        }

                        svcUser.save(user);
                        c.getValidationErrors().addGlobalError(new LocalizableError("profileUpdated"));
                    } else {
                        // TODO: Session expired?
                    }
                }

            } catch (WPServiceException e) {
                getContext().getValidationErrors().addGlobalError(new LocalizableError("unableToSaveProfile"));
                log.warning("unable to save user: " + user);
            }
        }
        // redirect back here
        return new ForwardResolution(VIEW);
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        SvcUser svcUser = SvcUserFactory.getUserService();
        if (email != null) {
            try {
                // TODO: make sure new email is unique
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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