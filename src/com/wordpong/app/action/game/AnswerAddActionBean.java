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
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.QuestionView;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class AnswerAddActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final Logger log = Logger.getLogger(AnswerAddActionBean.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/game/_answerAdd.jsp";

    private SvcGame _svcGame;

    private User user;

    public AnswerAddActionBean() {
        _svcGame = SvcGameFactory.getGameService();
    }

    @DontValidate
    public Resolution back() {
        return new RedirectResolution(AnswerListActionBean.class);
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
            // Todo: validate email list
        }
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

    public List<QuestionView> getQuestionList() {
        // user = getContext().getUserFromSession();
        List<QuestionView> result = new ArrayList<QuestionView>();
        List<Question> qs;
        try {
            qs = _svcGame.getQuestionsPublic();
            if (qs != null && qs.size() > 0) {
                for (Question q : qs) {
                    QuestionView a = new QuestionView();
                    String ks = q.getKeyString();
                    a.setQuestionKeyString(ks);
                    String d = q.getDescription();
                    a.setQuestionInfo(d);
                    result.add(a);
                }
            }
        } catch (WPServiceException e) {
            log.warning("getQuestionList user:" + user + " err:" + e);
        }
        return result;
    }
    @HandlesEvent("editAnswer")
    public Resolution editAnswer() {
        return new ForwardResolution(AnswerEditActionBean.class);
    }


}