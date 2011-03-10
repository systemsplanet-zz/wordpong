package com.wordpong.app.action.game;

import java.util.List;

import javax.annotation.security.PermitAll;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;

import com.wordpong.api.model.GameMyTurn;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class GameActionBean extends BaseActionBean {
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
    
    public User getUser() {
        return user;
    }

    public List<GameMyTurn> getMyTurns() {
        return _svcGame.getMyTurns();
    }

    public void setMyTurns(List<GameMyTurn> myTurns) {
        _svcGame.setMyTurns(myTurns);
    }

}