package com.wordpong.app.action.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.util.CryptoUtil;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.Question;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.util.debug.LogUtil;

public class QuestionEditActionBean extends BaseActionBean implements ValidationErrorHandler {
    private static final String VIEW = "/WEB-INF/jsp/game/_questionEdit.jsp";

    private SvcGame _svcGame;
    // when the user selects questions to edit these are populated
    private String questionKeyStringEncrypted;
    private String questionTitle;

    @Validate(required = true, maxlength = 100, minlength = 4)
    private String questionDescription;

    private Question question;
    private List<String> questions = null;

    private int questionsSize = 0;

    public QuestionEditActionBean() {
        _svcGame = SvcGameFactory.getSvcGame();
    }

    @After(stages = LifecycleStage.BindingAndValidation)
    public void doPostValidationStuff() {
        if (questionKeyStringEncrypted != null) {
            String questionKeyString = CryptoUtil.decrypt(questionKeyStringEncrypted);
            if (question == null && questionKeyString != null) {
                try {
                    question = _svcGame.getQuestion(questionKeyString);
                    if (question != null) {
                        Question q = _svcGame.getQuestion(question.getKeyString());
                        if (questions == null) {
                            questions = q.getQuestions();
                            if (questions != null) {
                                questionsSize = questions.size();
                            }
                            if (questions.size() == 0) {
                                List<String> as = question.getQuestions();
                                questions.addAll(as);
                            }
                            if (questionDescription == null) {
                                questionDescription = question.getDescription();
                            }
                        }
                    }
                } catch (WPServiceException e) {
                    LogUtil.logException("doPostValidationStuff", e);
                }
            }
        }
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
        try {
            if (question != null) {
                question.setDescription(questionDescription);
                question.setQuestions(questions);
                Map<String, Boolean> m = new HashMap<String, Boolean>();
                boolean duplicate = false;
                boolean allQuestioned = true;
                for (int i = 0; i < questionsSize; i++) {
                    if (questions.size() < questionsSize) {
                        allQuestioned = false;
                        break;
                    }
                    String a = questions.get(i);
                    if (a == null || a.trim().length() == 0) {
                        allQuestioned = false;
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
                } else if (allQuestioned == false) {
                    addGlobalActionError("questionAddEdit.pleaseCompleteAllQuestions");
                } else if (question != null) {
                    _svcGame.updateQuestion(question);
                    addGlobalActionMessage("questionEdit.questionsUpdated");
                    result = new ForwardResolution(GameActionBean.class);
                }
            }
        } catch (WPServiceException e) {
            addGlobalActionError("questionEdit.unableToSaveQuestions");
            LogUtil.logException("save", e);
        }
        return result;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getQuestionKeyStringEncrypted() {
        return questionKeyStringEncrypted;
    }

    public void setQuestionKeyStringEncrypted(String a) {
        this.questionKeyStringEncrypted = a;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<String> getQuestions() {
        return questions;

    }
}
