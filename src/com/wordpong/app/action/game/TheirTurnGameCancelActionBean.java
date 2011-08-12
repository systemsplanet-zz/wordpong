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

import com.wordpong.api.model.Game;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.util.debug.LogUtil;

public class TheirTurnGameCancelActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/game/_theirTurnGameCancel.jsp";

    private String gameKeyStringEncrypted;
    private Game game;

    public TheirTurnGameCancelActionBean() {
    }

    @After(stages = LifecycleStage.BindingAndValidation)
    public void doPostValidationStuff() {
        if (gameKeyStringEncrypted != null) {
            String gameKeyString = CryptoUtil.decrypt(gameKeyStringEncrypted);
            if (game == null && gameKeyString != null) {
                // todo read gameKeyString question
                try {
                    SvcGame _svcGame = SvcGameFactory.getSvcGame();
                    game = _svcGame.getGame(gameKeyString);
                } catch (WPServiceException e) {
                    LogUtil.logException("doPostValidationStuff", e);
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

    @HandlesEvent("cancelGame")
    public Resolution cancelGame() {
        Resolution result = new ForwardResolution(VIEW);
        if (game != null) {
            try {
                SvcGame sg = SvcGameFactory.getSvcGame();
                AppActionBeanContext c = getContext();
                User user = c.getUserFromSession();
                sg.cancelGameInvite(game, user);
                addGlobalActionMessage("theirTurnGameCancel.invitedCancelled");
                result = new ForwardResolution(GameActionBean.class);
            } catch (WPServiceException e) {
                addGlobalActionError("theirTurnGameCancel.unableToCancel");
                LogUtil.logException("cancelGame", e);
            }
        }
        return result;
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

    public String getGameKeyStringEncrypted() {
        return gameKeyStringEncrypted;
    }

    public void setGameKeyStringEncrypted(String gameKeyStringEncrypted) {
        this.gameKeyStringEncrypted = gameKeyStringEncrypted;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
