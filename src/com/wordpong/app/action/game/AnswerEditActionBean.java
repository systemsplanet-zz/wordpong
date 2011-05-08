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
    private String questionKeyString;

    // private User user;
    private List<String> answers = new ArrayList<String>(100);

    public AnswerEditActionBean() {
        _svcGame = SvcGameFactory.getGameService();
        for (int i = 0; i < 100; i++) {
            answers.add(null);
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

    public QuestionEdit getQuestionEdit() {
        // todo read questionKeyString question
        // create a QuestionEdit
        QuestionEdit result = new QuestionEdit();
        result.setQuestionKeyString(questionKeyString);
        List<String> qs = new ArrayList<String>();
        qs.add("Please enter your Eye Color and be specific as possible");
        qs.add("Hair Color");
        qs.add("Skin Color");
        qs.add("Car Color");
        result.setQuestions(qs);
        return result;
    }

    @HandlesEvent("save")
    public Resolution save() {
        
        QuestionEdit qe = getQuestionEdit();
        List<String> qs = qe.getQuestions();
        boolean allAnswered = true;
        for (int i = 0; i < qs.size(); i++) {
            if (answers.get(i) == null || answers.get(i).trim().length() == 0) {
                allAnswered = false;
                break;
            }
        }
        if (allAnswered) {
            // todo call svc_game to persist answers
            // create a answer object
            // point it to questionKeyString
            // persist to db
            addGlobalActionError("answerEdit.answersUpdated");
            log.info("updated answers:"+answers);
        } else {
            addGlobalActionError("answerEdit.pleaseAnswerAllQuestions");
        }
        return new ForwardResolution(VIEW);
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getQuestionKeyString() {
        return questionKeyString;
    }

    public void setQuestionKeyString(String questionKeyString) {
        this.questionKeyString = questionKeyString;
    }

}
