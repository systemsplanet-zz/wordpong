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
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.User;
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
    private String questionDescription;
    private QuestionEdit questionEdit;
    private List<String> questions;
    private int questionsSize = 0;
    private List<String> answers = new ArrayList<String>();

    public AnswerEditActionBean() {
        _svcGame = SvcGameFactory.getGameService();
        getQuestionEdit();
        for (int i = 0; i < questionsSize; i++) {
            answers.add("");
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
        if (questionEdit == null) {
            // todo read questionKeyString question
            // create a QuestionEdit
            questionEdit = new QuestionEdit();
            questions = new ArrayList<String>();
            questions.add("Please enter your Eye Color and be specific as possible");
            questions.add("Hair Color");
            questions.add("Skin Color");
            questions.add("Car Color");
            questionsSize = questions.size();
            questionEdit.setQuestions(questions);
        }
        return questionEdit;
    }

    @HandlesEvent("save")
    public Resolution save() {
        getQuestionEdit();
        boolean allAnswered = true;
        for (int i = 0; i < questionsSize; i++) {
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
            Answer a = new Answer();
            User u = getContext().getUserFromSession();
            a.setQuestionKeyString(questionKeyString);
            a.setAnswers(answers);
            a.setQuestionDescription(questionDescription);
            a.setUserKey(u.getKey());
            a.setLocaleString(u.getLocaleString());
            try {
                _svcGame.saveAnswer(a);
                addGlobalActionError("answerEdit.answersUpdated");
                log.info("updated answers:" + answers);
            } catch (WPServiceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

}
