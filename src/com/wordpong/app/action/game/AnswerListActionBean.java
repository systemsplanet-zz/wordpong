package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.AnswerView;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class AnswerListActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final Logger log = Logger.getLogger(AnswerListActionBean.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/game/_answerList.jsp";

    private SvcGame _svcGame;

    private User user;

    public AnswerListActionBean() {
        _svcGame = SvcGameFactory.getGameService();
    }

    @DontValidate
    public Resolution back() {
        return new ForwardResolution(GameActionBean.class);
    }

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(VIEW);
    }

    @HandlesEvent("addAnswer")
    public Resolution addAnswer() {
        return new ForwardResolution(AnswerAddActionBean.class);
    }

    @HandlesEvent("submit")
    public Resolution submit() {

        AppActionBeanContext c = getContext();
        if (c != null) {
            try {
                user = c.getUserFromSession();
                if (user != null) {
                    // TODO: parse answers
                    // Add answers
                    // Send emails
                    addGlobalActionError("answersInvited");
                } else {
                    // session expire?
                }
            } catch (Exception e) {
                addGlobalActionError("unableToInviteAnswers");
                log.warning("unable to invite answers");
            }
        }
        // redirect back here
        return new ForwardResolution(VIEW);
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        AppActionBeanContext c = getContext();
        if (c != null) {
            // TODO: validate
        }
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

    public List<AnswerView> getMyAnswerList() {
        List<AnswerView> result = new ArrayList<AnswerView>();
        user = getContext().getUserFromSession();
        log.info("user:" + user + " svc:" + _svcGame);
        try {
            // get the list of answers for this user
            List<Answer> as = _svcGame.getAnswers(user);
            if (as != null) {
                for (Answer a : as) {
                    AnswerView av = new AnswerView();
                    av.setId(a.getKeyString());
                    av.setQuestionDescription(a.getQuestionDescription());
                    //TODO: dont add if already answered
                    result.add(av);
                }
            }
        } catch (WPServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO populate using _svcGame
        return result;
    }

    // public void setMyTurns(List<GameMyTurn> myTurns) {
    // _svcGame.setMyTurns(myTurns);
    // }

}
