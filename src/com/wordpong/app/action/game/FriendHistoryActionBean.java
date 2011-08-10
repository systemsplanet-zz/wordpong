package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.util.CryptoUtil;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.Game;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class FriendHistoryActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final Logger log = Logger.getLogger(FriendHistoryActionBean.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/game/_friendHistory.jsp";

    private String friendHistoryKeyStringEncrypted = "?fhke?";
    private User user;
    private User friend;
    private List<Game> theirAnswers = new ArrayList<Game>();
    private List<Game> myAnswers = new ArrayList<Game>();

    public FriendHistoryActionBean() {
    }

    @After(stages = LifecycleStage.BindingAndValidation)
    public void doPostValidationStuff() {
        if (friendHistoryKeyStringEncrypted != null) {
            String friendKeyString = CryptoUtil.decrypt(friendHistoryKeyStringEncrypted);
            if (friendKeyString != null) {
                try {
                    SvcGame _svcGame = SvcGameFactory.getSvcGame();
                    friend = _svcGame.getUser(friendKeyString);
                    if (friend != null) {
                        user = getContext().getUserFromSession();
                        long start = System.currentTimeMillis();
                        theirAnswers = _svcGame.getFriendGames(friend, user);
                        int points = 0;
                        for (Game g : theirAnswers) {
                            points += g.getPoints();
                        }
                        myAnswers = _svcGame.getFriendGames(user, friend);
                        for (Game g : myAnswers) {
                            points += g.getPoints();
                        }
                        friend.setPoints(points);
                        log.info("getFriendGames elapsedMs:" + (System.currentTimeMillis() - start));                       
                    }
                } catch (WPServiceException e) {
                    log.warning("unable to get friend:" + e.getMessage());
                }
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

    public String getFriendHistoryKeyStringEncrypted() {
        return friendHistoryKeyStringEncrypted;
    }

    public void setFriendHistoryKeyStringEncrypted(String friendHistoryKeyStringEncrypted) {
        this.friendHistoryKeyStringEncrypted = friendHistoryKeyStringEncrypted;
    }

    public User getFriend() {
        return friend;
    }

    public List<Game> getTheirAnswers() {
        return theirAnswers;
    }

    public List<Game> getMyAnswers() {
        return myAnswers;
    }

}
