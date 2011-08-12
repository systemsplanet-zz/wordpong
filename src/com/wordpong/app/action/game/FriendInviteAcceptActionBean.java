package com.wordpong.app.action.game;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.util.CryptoUtil;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.util.debug.LogUtil;

public class FriendInviteAcceptActionBean extends BaseActionBean implements ValidationErrorHandler {

    private static final String VIEW = "/WEB-INF/jsp/game/_friendInviteAccept.jsp";

    private String inviteFriendKeyStringEncrypted;
    private String inviteFriendKeyString;
    private InviteFriend inviteFriend;

    public FriendInviteAcceptActionBean() {
    }

    @After(stages = LifecycleStage.BindingAndValidation)
    public void doPostValidationStuff() {
        if (inviteFriendKeyStringEncrypted != null) {
            inviteFriendKeyString = CryptoUtil.decrypt(inviteFriendKeyStringEncrypted);
            SvcGame sg = SvcGameFactory.getSvcGame();
            try {
                inviteFriend = sg.getInviteFriend(inviteFriendKeyString);
            } catch (WPServiceException e) {
                LogUtil.logException("doPostValidationStuff", e);
            }
        }
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
        Resolution result = new ForwardResolution(VIEW);
        try {
            // hide friendinvite from invitee
            SvcGame sg = SvcGameFactory.getSvcGame();
            sg.ignoreFriendInvitation(inviteFriendKeyString);
            addGlobalActionMessage("friendInviteAccept.inviteIgnored");
            result = new ForwardResolution(GameActionBean.class);
        } catch (WPServiceException e) {
            addGlobalActionError("friendInviteAccept.unableToIgnore");
            LogUtil.logException("ignoreInvite", e);
        }
        return result;
    }

    @HandlesEvent("acceptInviteConfirm")
    public Resolution acceptInvite() {
        Resolution result = new ForwardResolution(GameActionBean.class);
        AppActionBeanContext c = getContext();
        if (c != null) {
            try {
                User user = c.getUserFromSession();
                if (user != null) {
                    SvcGame sg = SvcGameFactory.getSvcGame();
                    sg.makeFriends(inviteFriendKeyString); // inviteFriendKey
                    SvcUser su = SvcUserFactory.getSvcUser();
                    // get user with new friend attached
                    user = su.getByKey(user);
                    c.putUserToRequestAndSession(user);
                    addGlobalActionMessage("friendInviteAccept.inviteAccepted");
                } else {
                    // session expire?
                }
            } catch (Exception e) {
                addGlobalActionError("friendInviteAccept.unableToAccept");
                LogUtil.logException("acceptInviteConfirm", e);
            }
        }
        // redirect back here
        return result;
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

    public String getInviteFriendKeyStringEncrypted() {
        return inviteFriendKeyStringEncrypted;
    }

    public void setInviteFriendKeyStringEncrypted(String inviteFriendKeyStringEncrypted) {
        this.inviteFriendKeyStringEncrypted = inviteFriendKeyStringEncrypted;
    }

    public InviteFriend getInviteFriend() {
        return inviteFriend;
    }

    public void setInviteFriend(InviteFriend inviteFriend) {
        this.inviteFriend = inviteFriend;
    }

}
