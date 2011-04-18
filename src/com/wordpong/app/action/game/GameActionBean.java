package com.wordpong.app.action.game;

import java.util.List;
//import java.util.logging.Logger;

import javax.annotation.security.PermitAll;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;

import com.wordpong.api.model.User;
import com.wordpong.api.pojo.GameMyTurn;
import com.wordpong.api.pojo.GameTheirTurn;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class GameActionBean extends BaseActionBean {
    //private static final Logger log = Logger.getLogger(GameActionBean.class.getName());
    public static final String VIEW = "/WEB-INF/jsp/game/index.jsp";

    private SvcGame _svcGame;
    private User user;

    public GameActionBean() {
        _svcGame = SvcGameFactory.getGameService();
    }

    // Make sure user is authenticated
    @Before(stages = { LifecycleStage.BindingAndValidation })
    public Resolution authorizeFilter() {
        return assertAuthenticated();
    }

    // Requires authenticated user
    @PermitAll
    @DefaultHandler
    public Resolution showMe() {
        AppActionBeanContext c = getContext();
        if (user == null) {
            user = c.getUserFromSession();
        }
        return new ForwardResolution(VIEW);
    }

    @DontValidate
    public Resolution theirTurnSelect() {
        // TODO
        return new ForwardResolution(ProfileEditActionBean.class);
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
    @HandlesEvent("viewInvite")
    public Resolution viewInvite() {
        return new ForwardResolution(FriendInviteCancelActionBean.class);
    }

    @DontValidate
    @HandlesEvent("acceptInvite")
    public Resolution acceptInvite() {
        return new ForwardResolution(FriendInviteAcceptActionBean.class);
    }

    public User getUser() {
        return user;
    }

    public List<GameMyTurn> getMyTurns() {
        user = getContext().getUserFromSession();
        return _svcGame.getMyTurns(user);
    }

    public List<GameTheirTurn> getTheirTurns() {
        user = getContext().getUserFromSession();
        return _svcGame.getTheirTurns(user);
    }

//    public void setMyTurns(List<GameMyTurn> myTurns) {
//        _svcGame.setMyTurns(myTurns);
//    }

}