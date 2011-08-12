package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.util.debug.LogUtil;

public class FriendListActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/game/_friendList.jsp";

    private SvcGame _svcGame;

    private User user;

    public FriendListActionBean() {
        _svcGame = SvcGameFactory.getSvcGame();
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

    @HandlesEvent("selectFriend")
    public Resolution selectFriend() {
        return new ForwardResolution(VIEW);
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        AppActionBeanContext c = getContext();
        if (c != null) {
        }
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

    public List<User> getMyFriends() throws WPServiceException {
        List<User> result = new ArrayList<User>();
        try {
            user = getContext().getUserFromSession();
            result = _svcGame.getMyFriends(user);
        } catch (WPServiceException e) {
            LogUtil.logException("getMyFriends", e);
        }
        return result;
    }
}
