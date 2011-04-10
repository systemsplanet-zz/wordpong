package com.wordpong.app.action.game;

import java.util.ArrayList;
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

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.GameMyTurn;
import com.wordpong.api.pojo.GameTheirTurn;
import com.wordpong.api.pojo.GameTheirTurn.Action;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class GameActionBean extends BaseActionBean {
    private static final Logger log = Logger.getLogger(GameActionBean.class.getName());
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
        if (c != null) {
            if (user == null) {
                user = c.getUserFromSession();
            }
        }
        return new ForwardResolution(VIEW);
    }
    @DontValidate
    public Resolution theirTurnSelect() {
        //TODO
        return new ForwardResolution(ProfileEditActionBean.class);
    }
    @DontValidate
    public Resolution myTurnSelect() {
        //TODO
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
        // todo: save email in session?
        return new ForwardResolution(FriendInviteCancelActionBean.class);
    }
    
    public User getUser() {
        return user;
    }

    public List<GameMyTurn> getMyTurns() {
        return _svcGame.getMyTurns();
    }

    public List<GameTheirTurn> getTheirTurns() {
        List<GameTheirTurn> turns = new ArrayList<GameTheirTurn>();
        try {
            List<FriendInvite> invites = _svcGame.getFriendInvites(user);
            if (invites != null) {
                for (FriendInvite fi : invites) {
                    GameTheirTurn gtt = new GameTheirTurn();
                    gtt.setId(fi.getInviteeEmail());
                    gtt.setAction(Action.InvitationSent);
                    turns.add(gtt);
                }
            }
        } catch (WPServiceException e) {
            log.fine("err:" + e.getMessage());
        }
        return turns;
    }

    public void setMyTurns(List<GameMyTurn> myTurns) {
        _svcGame.setMyTurns(myTurns);
    }

}