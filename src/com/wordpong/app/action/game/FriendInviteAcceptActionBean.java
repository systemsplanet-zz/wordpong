package com.wordpong.app.action.game;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class FriendInviteAcceptActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/game/_friendInviteAccept.jsp";

    @Validate(required = true, converter = EmailTypeConverter.class, minlength = 4, maxlength = 50)
    private String email;
    private String key;
    private String createdAtString="??";

    public FriendInviteAcceptActionBean() {
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

    @HandlesEvent("ignoreInvite")
    public Resolution ignoreInvite() {
        try {
            // hide friendinvite from invitee
            SvcGame sg = SvcGameFactory.getGameService();
            sg.ignoreInvitation(key);
            addGlobalActionError("friendInviteAccept.inviteIgnored");
        } catch (WPServiceException e) {
            addGlobalActionError("friendInviteAccept.unableToIgnore");
        }
        return new ForwardResolution(VIEW);
    }

    @HandlesEvent("acceptInvite")
    public Resolution acceptInvite() {
        AppActionBeanContext c = getContext();
        if (c != null) {
//            try {
//                user = c.getUserFromSession();
//                if (user != null) {
//                    String url = "https://wordpong.appspot.com/?register=" + friend.email;
//                    String msg = getMsg("friendInviteAccept.email.message", new Object[] { friend.getFullName(), url });
//                    String sub = getMsg("friendInviteAccept.email.subject", new Object[] { friend.getFullName() });
//                    List<String> emails = new ArrayList<String>();
//                    emails.add(friend.email);
//                    MailUtil.sendAdminMail(new EmailMessage(sub, msg, friend.email, user.getFullName()));
//                    addGlobalActionError("friendInviteAccept.inviteAccepted");
//                } else {
//                    // session expire?
//                }
//            } catch (Exception e) {
//                addGlobalActionError("friendInviteAccept.unableToAccept");
//                log.warning("unable to invite friend");
//            }
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCreatedAtString() {
        return createdAtString;
    }

    public void setCreatedAtString(String createdAtString) {
        this.createdAtString = createdAtString;
    }    
}
