package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;

public class QuestionAddEditActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final Logger log = Logger.getLogger(QuestionAddEditActionBean.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/game/_questionAddEdit.jsp";

    // TODO: how do we add more questions
    private static final Integer QUESTIONS = 4;

    // FORM VARIABLES
    @Validate(required = true, maxlength = 100, minlength = 5)
    private String title;

    @Validate(required = true, maxlength = 100, minlength = 5)
    private String description;

    //@Validate(required = true)  //TODO: allow public questions once rating system is in place
    private int visibility = Question.VISIBILITY_PRIVATE;

    @Validate(required = true)
    private int intimacyLevel = Question.INTIMACY_CLICHES;

    @Validate(required = true, maxlength = 100, minlength = 5)
    private List<String> questions;

    private int questionsSize = 0;

    public QuestionAddEditActionBean() {
    }

    @After(stages = LifecycleStage.BindingAndValidation)
    public void doPostValidationStuff() {
        if (questions == null) {
            questions = new ArrayList<String>();
            for (int i = 0; i < QUESTIONS; i++) {
                questions.add("");
            }
        }
        if (questions.size() != QUESTIONS) {
            for (int i = 0; i < QUESTIONS - questions.size() + 1; i++) {
                questions.add("");
            }
        }
        questionsSize = questions.size();
    }

    @DontValidate
    public Resolution back() {
        return new RedirectResolution(QuestionListActionBean.class);
    }

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(VIEW);
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
    }

    // on errors, only reply with the content, not the entire page
    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(VIEW);
    }

    @HandlesEvent("save")
    public Resolution save() {
        Resolution result = new ForwardResolution(VIEW);
        boolean allEntered = true;
        boolean duplicate = false;
        Map<String, Boolean> m = new HashMap<String, Boolean>();
        for (int i = 0; i < questionsSize; i++) {
            if (questions.size() < questionsSize) {
                allEntered = false;
                break;
            }
            String a = questions.get(i);
            if (a == null || a.trim().length() == 0) {
                allEntered = false;
                break;
            }
            a = a.trim();
            if (m.containsKey(a)) {
                duplicate = true;
                break;
            }
            m.put(a, true);
        }
        if (duplicate) {
            addGlobalActionError("questionAddEdit.pleaseMakeAllQuestionsUnique");
        } else if (allEntered == false) {
            addGlobalActionError("questionAddEdit.pleaseCompleteAllQuestions");
        } else {
            User u = getContext().getUserFromSession();

            // create a question object
            Question q = new Question();
            q.setQuestions(questions);
            q.setTitle(title);
            q.setDescription(description);
            q.setVisibility(visibility);
            q.setIntimacyLevel(intimacyLevel);
            q.setUser(u);
            q.setLocaleString(u.getLocaleString());
            try {
                SvcGame _svcGame = SvcGameFactory.getSvcGame();
                // call svc_game to persist questions
                 _svcGame.createQuestion(q);                
                //addGlobalActionMessage("questionAddEdit.questionsUpdated");
                log.info("created questions:" + questions);
                Map<String,String> params = new HashMap<String,String>();
                params.put("questionKeyStringEncrypted", q.getKeyStringEncrypted());
                params.put("questionTitle", q.getTitle());
                result = new ForwardResolution(AnswerAddEditActionBean.class).addParameters(params);
            } catch (WPServiceException e) {
                // Duplicate title?
                addGlobalActionError("questionAddEdit.unableToCreate");
            }
        }
        return result;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getIntimacyLevel() {
        return intimacyLevel;
    }

    public void setIntimacyLevel(int intimacyLevel) {
        this.intimacyLevel = intimacyLevel;
    }

}
