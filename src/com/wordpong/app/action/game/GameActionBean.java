package com.wordpong.app.action.game;

import java.util.List;
import java.util.logging.Logger;

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

public class GameActionBean extends BaseActionBean {
    private static final Logger log = Logger.getLogger(GameActionBean.class.getName());
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
            log.warning("getUser err:" + e.getMessage());
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
    public Resolution theirTurnSelect() {
        // TODO
        return new ForwardResolution(ProfileEditActionBean.class);
    }

    @DontValidate
    @HandlesEvent("addGame")
    public Resolution addGame() {
        return new ForwardResolution(NewGameActionBean.class);
    }

    @DontValidate
    public Resolution myTurnSelect() {
        // TODO
        return new ForwardResolution(ProfileEditActionBean.class);
    }

    @DontValidate
    public Resolution profileEdit() {
        return new ForwardResolution(ProfileEditActionBean.class);
    }

    @DontValidate
    public Resolution friendList() {
        return new ForwardResolution(FriendListActionBean.class);
    }

    @DontValidate
    public Resolution answerList() {
        return new ForwardResolution(AnswerListActionBean.class);
    }
    @DontValidate
    public Resolution questionList() {
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

    public List<InviteFriend> getMyTurnInviteFriends() throws WPServiceException {
        return _svcGame.getMyTurnInviteFriends(user);
    }


    public List<Game> getMyTurnGames() throws WPServiceException {
        return _svcGame.getMyTurnGames(user);
    }

    public List<InviteFriend> getTheirTurnsInviteFriend() throws WPServiceException {
        user = getContext().getUserFromSession();
        return _svcGame.getTheirTurnsInviteFriend(user);
    }
    
    public List<Game> getTheirTurnsGame() throws WPServiceException {
        return _svcGame.getTheirTurnsGame(user);
    }
    
    public User getUser() {
        return user;
    }


}