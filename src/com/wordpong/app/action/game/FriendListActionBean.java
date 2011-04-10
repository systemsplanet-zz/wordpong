package com.wordpong.app.action.game;

import java.util.List;
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

import com.wordpong.api.model.User;
import com.wordpong.api.pojo.GameMyTurn;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class FriendListActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final Logger log = Logger.getLogger(FriendListActionBean.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/game/_friendList.jsp";

    private SvcGame _svcGame;
    
    private User user;

    @Validate(required = true, minlength = 4, maxlength = 50)
    private String emails;

    public FriendListActionBean() {
        _svcGame = SvcGameFactory.getGameService();
    }

    @DontValidate
    public Resolution back() {
        return new ForwardResolution(GameActionBean.class);
    }
    @DontValidate
    public Resolution friendInvite() {
        return new ForwardResolution(FriendInviteActionBean.class);
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
                    // TODO: parse friends
                    // Add friends
                    // Send emails
                    addGlobalActionError("friendsInvited");
                } else {
                    // session expire?
                }
            } catch (Exception e) {
                addGlobalActionError("unableToInviteFriends");
                log.warning("unable to invite friends");
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

    public String getEmails() {
        return emails;
    }

    public void setEmails(String e) {
        emails = e;
    }

    public List<GameMyTurn> getMyTurns() {
        return _svcGame.getMyTurns();
    }
    public void setMyTurns(List<GameMyTurn> myTurns) {
        _svcGame.setMyTurns(myTurns);
    }

}
