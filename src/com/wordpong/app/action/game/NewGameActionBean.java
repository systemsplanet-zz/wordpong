package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.Answer;
import com.wordpong.api.model.Game;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.util.debug.LogUtil;

public class NewGameActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String SELECT_FRIEND = "/WEB-INF/jsp/game/_newGame.jsp";
    private static final String SELECT_ANSWER = "/WEB-INF/jsp/game/_newGame_Answer.jsp";
    private static final String CONFIRM_GAME = "/WEB-INF/jsp/game/_newGame_Confirm.jsp";
    private SvcGame _svcGame;

    private User user;
    private List<User> friends = new ArrayList<User>();
    private List<Answer> answers = new ArrayList<Answer>();
    private String friendKeyStringEncrypted = null;
    private String friendDetails = null;
    private String friendPictureUrl = null;
    private String questionTitle;
    private String answerKeyStringEncrypted;

    public NewGameActionBean() {
        _svcGame = SvcGameFactory.getSvcGame();
    }

    @After(stages = LifecycleStage.BindingAndValidation)
    public void doPostValidationStuff() {
        AppActionBeanContext c = getContext();
        if (user == null) {
            user = c.getUserFromSession();
            try {
                friends = _svcGame.getMyFriends(user);
            } catch (WPServiceException e) {
                LogUtil.logException("doPostValidationStuff err", e);
            }
        }
    }

    @DontValidate
    public Resolution back() {
        return new ForwardResolution(GameActionBean.class);
    }

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        Resolution result = new ForwardResolution(SELECT_FRIEND);
        // if no friends, forward to add friend page
        if (friends == null || friends.size() == 0) {
            result = new ForwardResolution(FriendInviteActionBean.class);
        } else {
            // if no answers, forward to add an answer
            try {
                answers = _svcGame.getAnswers(user);
            } catch (WPServiceException e) {
                LogUtil.logException("view", e);
            }
            if (answers == null || answers.size() == 0) {
                result = new ForwardResolution(AnswerAddActionBean.class);
            }
        }
        return result;
    }

    public List<Answer> getAnswers() {
        List<Answer> result = new ArrayList<Answer>();
        user = getContext().getUserFromSession();
        try {
            // get the list of answers for this user
            result = _svcGame.getAnswers(user);
        } catch (WPServiceException e) {
            LogUtil.logException("getAnswers", e);
        }
        return result;
    }

    @HandlesEvent("friendInvite")
    public Resolution friendInvite() {
        return new ForwardResolution(FriendInviteActionBean.class);
    }

    @HandlesEvent("addAnswer")
    public Resolution addAnswer() {
        return new ForwardResolution(AnswerAddActionBean.class);
    }

    @HandlesEvent("selectFriend")
    public Resolution selectFriend() {
        return new ForwardResolution(SELECT_ANSWER);
    }

    @HandlesEvent("selectAnswer")
    public Resolution selectAnswer() {
        Resolution resolution = new ForwardResolution(CONFIRM_GAME);
        return resolution;
    }

    @HandlesEvent("startGame")
    public Resolution startGame() {
        SvcGame sg = SvcGameFactory.getSvcGame();
        try {
            String inviterDetails = user.getDetails();
            String inviterPictureUrl = user.getPictureUrl();
            Game g = new Game();
            g.setInviterDetails(inviterDetails);
            g.setInviterPictureUrl(inviterPictureUrl);
            g.setQuestionTitle(questionTitle);
            g.setAnswersKeyEncryptedString(answerKeyStringEncrypted);
            g.setInviteeKeyEncryptedString(friendKeyStringEncrypted);
            g.setInviteeDetails(friendDetails);
            g.setInviteePictureUrl(friendPictureUrl);
            g.setInviterUserKeyString(user.getKeyString());
            sg.createGame(g, user);
            addGlobalActionMessage("newGame.gameSent");
        } catch (Exception e) {
            LogUtil.logException("startGame", e);
            addGlobalActionError("newGame.unableToStartGame");
        }
        // redirect home
        return new ForwardResolution(GameActionBean.class);
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        AppActionBeanContext c = getContext();
        if (c != null) {
        }
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(SELECT_FRIEND);
    }

    public List<User> getMyFriends() {
        return friends;
    }

    public String getFriendKeyStringEncrypted() {
        return friendKeyStringEncrypted;
    }

    public void setFriendKeyStringEncrypted(String friendKeyStringEncrypted) {
        this.friendKeyStringEncrypted = friendKeyStringEncrypted;
    }

    public String getFriendDetails() {
        return friendDetails;
    }

    public void setFriendDetails(String friendDetails) {
        this.friendDetails = friendDetails;
    }

    public String getFriendPictureUrl() {
        return friendPictureUrl;
    }

    public void setFriendPictureUrl(String friendPictureUrl) {
        this.friendPictureUrl = friendPictureUrl;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getAnswerKeyStringEncrypted() {
        return answerKeyStringEncrypted;
    }

    public void setAnswerKeyStringEncrypted(String answerKeyStringEncrypted) {
        this.answerKeyStringEncrypted = answerKeyStringEncrypted;
    }

}
