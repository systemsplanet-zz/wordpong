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

import com.wordpong.api.pojo.QuestionEdit;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class AnswerEditActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final Logger log = Logger.getLogger(AnswerEditActionBean.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/game/_answerEdit.jsp";

    private SvcGame _svcGame;

    // private User user;
    private List<String> fields = new ArrayList<String>(100);

    public AnswerEditActionBean() {
        _svcGame = SvcGameFactory.getGameService();
        for (int i = 0; i < 100; i++) {
            fields.add(null);
        }
    }

    @DontValidate
    public Resolution back() {
        return new RedirectResolution(AnswerAddActionBean.class);
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

    public List<QuestionEdit> getQuestionList() {
        List<QuestionEdit> result = new ArrayList<QuestionEdit>();
        QuestionEdit a = new QuestionEdit();
        a.setQuestion("Please enter your Eye Color and be specific as possible");
        result.add(a);
        a = new QuestionEdit();
        a.setQuestion("Hair Color");
        result.add(a);
        a = new QuestionEdit();
        a.setQuestion("Skin Color");
        result.add(a);
        a = new QuestionEdit();
        a.setQuestion("Car Color");
        result.add(a);
        return result;
    }

    @HandlesEvent("save")
    public Resolution save() {
        List<QuestionEdit> qs = getQuestionList();
        boolean allAnswered = true;
        for (int i = 0; i < qs.size(); i++) {
            if (fields.get(i) == null || fields.get(i).trim().length() == 0) {
                allAnswered = false;
                break;
            }
        }
        if (allAnswered) {
            addGlobalActionError("answerEdit.answersUpdated");
        } else {
            addGlobalActionError("answerEdit.pleaseAnswerAllQuestions");
        }
        return new ForwardResolution(VIEW);
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

}
