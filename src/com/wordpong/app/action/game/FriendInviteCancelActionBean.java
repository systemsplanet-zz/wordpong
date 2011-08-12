package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.servlet.msg.EmailMessage;
import com.wordpong.app.servlet.msg.MailUtil;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.util.debug.LogUtil;

public class FriendInviteCancelActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/game/_friendInviteCancel.jsp";

    private User user;

    @Validate(required = true, converter = EmailTypeConverter.class, minlength = 4, maxlength = 50)
    private String email;
    private String createdAtString = "??";

    public FriendInviteCancelActionBean() {
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

    @HandlesEvent("cancelInvite")
    public Resolution cancelInvite() {
        AppActionBeanContext c = getContext();
        if (c != null) {
            user = c.getUserFromSession();
            if (user != null) {
                try {
                    // remove from friendinvite from db
                    SvcGame sg = SvcGameFactory.getSvcGame();
                    sg.cancelFriendInvitation(user, email);
                    addGlobalActionError("friendInviteCancel.invitedCancelled");
                } catch (WPServiceException e) {
                    LogUtil.logException("cancelInvite", e);
                    addGlobalActionError("friendInviteCancel.unableToCancel");
                }
            }
        }
        return new ForwardResolution(VIEW);
    }

    @HandlesEvent("resendInvite")
    public Resolution resendInvite() {
        AppActionBeanContext c = getContext();
        if (c != null) {
            try {
                user = c.getUserFromSession();
                if (user != null) {
                    String url = "https://wordpong.appspot.com/?register=" + email;
                    String msg = getMsg("friendInvite.email.message", new Object[] { user.getFullName(), url });
                    String sub = getMsg("friendInvite.email.subject", new Object[] { user.getFullName() });
                    List<String> emails = new ArrayList<String>();
                    emails.add(email);
                    MailUtil.sendAdminMail(new EmailMessage(sub, msg, email, user.getFullName()));
                    addGlobalActionError("friendInvite.friendInvited");
                } else {
                    // session expire?
                }
            } catch (Exception e) {
                addGlobalActionError("friendInvite.unableToInviteFriend");
                LogUtil.logException("resendInvite", e);
            }
        }
        // redirect back here
        return new ForwardResolution(VIEW);
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

    public String getCreatedAtString() {
        return createdAtString;
    }

    public void setCreatedAtString(String createdAtString) {
        this.createdAtString = createdAtString;
    }

}
