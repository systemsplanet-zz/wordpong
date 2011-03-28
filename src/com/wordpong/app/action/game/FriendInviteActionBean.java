package com.wordpong.app.action.game;

import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class FriendInviteActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final Logger log = Logger.getLogger(FriendInviteActionBean.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/game/_friendInvite.jsp";

    private SvcUser svcUser;

    private User user;

    @Validate(required = true, converter = EmailTypeConverter.class, minlength = 4, maxlength = 50)
    private String email;

    public FriendInviteActionBean() {
        svcUser = SvcUserFactory.getUserService();
    }

    @DontValidate
    public Resolution back() {
        return new ForwardResolution(FriendListActionBean.class);
    }

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(VIEW);
    }

    @HandlesEvent("submit")
    public Resolution submit() {
        AppActionBeanContext c = getContext();
        if (c != null) {
            try {
                user = c.getUserFromSession();
                if (user != null) {
                    // TODO: parse friend
                    // Add friend
                    // Send email
                    addGlobalActionError("friendInvite.friendInvited");
                } else {
                    // session expire?
                }
            } catch (Exception e) {
                addGlobalActionError("friendInvite.unableToInviteFriend");
                log.warning("unable to invite friend");
            }
        }
        // redirect back here
        return new ForwardResolution(VIEW);
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        AppActionBeanContext c = getContext();
        if (c != null) {
            //Todo: validate email list
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
