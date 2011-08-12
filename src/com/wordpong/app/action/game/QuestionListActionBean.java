package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.util.debug.LogUtil;

public class QuestionListActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/game/_questionList.jsp";

    private SvcGame _svcGame;
    private User user;

    // when the user selects questions to edit this is populated
    private String questionTitle;

    public QuestionListActionBean() {
        _svcGame = SvcGameFactory.getSvcGame();
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

    @HandlesEvent("addQuestion")
    public Resolution addQuestion() {
        return new ForwardResolution(QuestionAddEditActionBean.class);
    }

    @HandlesEvent("editQuestions")
    public Resolution editQuestions() {
        Resolution resolution = new ForwardResolution(VIEW);
        AppActionBeanContext c = getContext();
        if (c != null) {
            try {
                user = c.getUserFromSession();
                if (user != null) {
                    resolution = new ForwardResolution(QuestionEditActionBean.class);
                } else {
                    // session expire?
                }
            } catch (Exception e) {
                addGlobalActionError("unableToEditQuestions");
                LogUtil.logException("editQuestions", e);
            }
        }
        // redirect back here
        return resolution;
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

    public List<Question> getQuestions() {
        List<Question> result = new ArrayList<Question>();
        user = getContext().getUserFromSession();
        try {
            // get the list of questions for this user
            result = _svcGame.getMyQuestions(user);
        } catch (WPServiceException e) {
            LogUtil.logException("getQuestions", e);
        }
        return result;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

}
