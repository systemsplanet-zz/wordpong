package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

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
            // Todo: validate email list
        }
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }


    public List<AnswerView> getMyAnswerList() {
        user = getContext().getUserFromSession();

        List<AnswerView> result = new ArrayList<AnswerView>();
        AnswerView a = new AnswerView();
        a.setId("id1");
        a.setAnswerInfo("Favorite Colors");
        result.add(a);
        a = new AnswerView();
        a.setId("id2");
        a.setAnswerInfo("Favorite Dates");
        result.add(a);
        // TODO populate using _svcGame
        return result;
    }

    // public void setMyTurns(List<GameMyTurn> myTurns) {
    // _svcGame.setMyTurns(myTurns);
    // }

}
