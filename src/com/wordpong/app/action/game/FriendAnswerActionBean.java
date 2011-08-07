package com.wordpong.app.action.game;

import java.util.logging.Logger;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.util.CryptoUtil;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.Answer;
import com.wordpong.api.model.Game;
import com.wordpong.api.model.Question;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;

public class FriendAnswerActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final Logger log = Logger.getLogger(FriendAnswerActionBean.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/game/_friendAnswer.jsp";

    private String gameKeyStringEncrypted;
    private Game game;
    private Question question;
    private Answer answer;

    public FriendAnswerActionBean() {
    }

    @After(stages = LifecycleStage.BindingAndValidation)
    public void doPostValidationStuff() {
        if (gameKeyStringEncrypted != null) {
            String gameKeyString = CryptoUtil.decrypt(gameKeyStringEncrypted);
            if (gameKeyString != null) {
                try {
                    SvcGame _svcGame = SvcGameFactory.getSvcGame();
                    game = _svcGame.getGame(gameKeyString);
                    if (game != null) {
                        answer = _svcGame.getAnswer(game.getAnswersKeyString());
                        if (answer != null) {
                            question = _svcGame.getQuestion(answer.getQuestionKeyString());
                        }
                    }
                } catch (WPServiceException e) {
                    log.warning("unable to get answer:" + e.getMessage());
                }
            }
        }
    }

    @DontValidate
    public Resolution back() {
        return new RedirectResolution(FriendListActionBean.class);
    }

    @DontValidate
    @DefaultHandler
    public Resolution selectGame() {
        return new ForwardResolution(VIEW);
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getGameKeyStringEncrypted() {
        return gameKeyStringEncrypted;
    }

    public void setGameKeyStringEncrypted(String a) {
        this.gameKeyStringEncrypted = a;
    }
}
