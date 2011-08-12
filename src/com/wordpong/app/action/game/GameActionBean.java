package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;

import com.wordpong.api.model.Game;
import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.util.debug.LogUtil;

public class GameActionBean extends BaseActionBean {
    public static final String VIEW = "/WEB-INF/jsp/game/index.jsp";

    private SvcGame _svcGame;
    private User user;

    public GameActionBean() {
        _svcGame = SvcGameFactory.getSvcGame();
    }

    // Make sure user is authenticated
    @Before(stages = { LifecycleStage.BindingAndValidation })
    public Resolution authorizeFilter() {
        AppActionBeanContext c = getContext();
        try {
            if (user == null) {
                user = c.getUserFromSession();
            }
            // refresh user
            user = _svcGame.getUser(user);
            c.putUserToRequestAndSession(user);
        } catch (WPServiceException e) {
            LogUtil.logException("authorizeFilter", e);
        }
        return assertAuthenticated();
    }

    // Requires authenticated user
    @PermitAll
    @DefaultHandler
    public Resolution showMe() {
        return new ForwardResolution(VIEW);
    }

    @DontValidate
    public Resolution profileBtn() {
        return new ForwardResolution(ProfileEditActionBean.class);
    }

    @DontValidate
    public Resolution addGameBtn() {
        return new ForwardResolution(NewGameActionBean.class);
    }

    @DontValidate
    public Resolution myTurnSelect() {
        return new ForwardResolution(ProfileEditActionBean.class);
    }

    @DontValidate
    public Resolution profileEdit() {
        return new ForwardResolution(ProfileEditActionBean.class);
    }

    @DontValidate
    public Resolution friendsBtn() {
        return new ForwardResolution(FriendListActionBean.class);
    }

    @DontValidate
    public Resolution answersBtn() {
        return new ForwardResolution(AnswerListActionBean.class);
    }

    @DontValidate
    public Resolution questionsBtn() {
        return new ForwardResolution(QuestionListActionBean.class);
    }

    @DontValidate
    @HandlesEvent("processFriendInvite")
    public Resolution processFriendInvite() {
        return new ForwardResolution(FriendInviteAcceptActionBean.class);
    }

    @DontValidate
    @HandlesEvent("viewTheirTurnFriendInvite")
    public Resolution viewTheirTurnFriendInvite() {
        return new ForwardResolution(TheirTurnFriendInviteActionBean.class);
    }

    @DontValidate
    @HandlesEvent("theirTurnGameCancel")
    public Resolution theirTurnGameCancel() {
        return new ForwardResolution(TheirTurnGameCancelActionBean.class);
    }

    @DontValidate
    @HandlesEvent("playGame")
    public Resolution playGame() {
        // TODO: select different game presentations modes here
        return new ForwardResolution(GamePlayActionBean.class);
    }

    public List<InviteFriend> getMyTurnInviteFriends() {
        List<InviteFriend> result = new ArrayList<InviteFriend>();
        try {
            result = _svcGame.getMyTurnInviteFriends(user);
        } catch (WPServiceException e) {
            LogUtil.logException("getMyTurnInviteFriends", e);
        }
        return result;
    }

    public List<Game> getMyTurnGames() throws WPServiceException {
        List<Game> result = new ArrayList<Game>();
        try {
            result = _svcGame.getMyTurnGames(user);
        } catch (WPServiceException e) {
            LogUtil.logException("getMyTurnInviteFriends", e);
        }
        return result;
    }

    public List<InviteFriend> getTheirTurnsInviteFriend() throws WPServiceException {
        List<InviteFriend> result = new ArrayList<InviteFriend>();
        try {
            user = getContext().getUserFromSession();
            result = _svcGame.getTheirTurnsInviteFriend(user);
        } catch (WPServiceException e) {
            LogUtil.logException("getMyTurnInviteFriends", e);
        }
        return result;
    }

    public List<Game> getTheirTurnsGame() throws WPServiceException {
        List<Game> result = new ArrayList<Game>();
        try {
            result = _svcGame.getTheirTurnsGame(user);
        } catch (WPServiceException e) {
            LogUtil.logException("getMyTurnInviteFriends", e);
        }
        return result;
    }

    public User getUser() {
        return user;
    }

}