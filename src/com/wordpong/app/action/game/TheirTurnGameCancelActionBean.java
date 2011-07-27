package com.wordpong.app.action.game;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

import com.wordpong.api.model.User;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class TheirTurnGameCancelActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/game/_theirTurnGameCancel.jsp";

    private User user;

    public TheirTurnGameCancelActionBean() {
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
    public Resolution cancelGame() {
        AppActionBeanContext c = getContext();
        if (c != null) {
            user = c.getUserFromSession();
            if (user != null) {
                //log.info("cancel game invitation");
                // TODO: cancel the game
                // try {
                // SvcGame sg = SvcGameFactory.getGameService();
                // addGlobalActionError("friendInviteCancel.invitedCancelled");
                // } catch (WPServiceException e) {
                // addGlobalActionError("friendInviteCancel.unableToCancel");
                // }
            }
        }
        return new ForwardResolution(VIEW);
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

}
