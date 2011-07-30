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
import net.sourceforge.stripes.util.CryptoUtil;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.model.Answer;
import com.wordpong.api.model.Question;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;

public class AnswerEditActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(AnswerEditActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_answerEdit.jsp";

	private SvcGame _svcGame;
	// when the user selects answers to edit these are populated
	private String answerKeyStringEncrypted;
	private String questionDescription;
	private Answer answer;
	private List<String> answers = new ArrayList<String>();

	private List<String> questions;
	private int questionsSize = 0;

	public AnswerEditActionBean() {
		_svcGame = SvcGameFactory.getSvcGame();
	}

	@After(stages = LifecycleStage.BindingAndValidation)
	public void doPostValidationStuff() {
		if (answerKeyStringEncrypted != null) {
			String answerKeyString = CryptoUtil.decrypt(answerKeyStringEncrypted);
			if (answer == null && answerKeyString != null) {
				try {
					answer = _svcGame.getAnswer(answerKeyString);
					if (answer != null) {
						Question q = _svcGame.getQuestion(answer
								.getQuestionKeyString());
						questions = q.getQuestions();
						if (questions != null) {
							questionsSize = questions.size();
						}
						if (answers.size() == 0) {
							List<String> as = answer.getAnswers();
							answers.addAll(as);
						}
					}
				} catch (WPServiceException e) {
					log.warning("unable to get answer:" + e.getMessage());
				}
			}
		}
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
	}

	// on errors, only reply with the content, not the entire page
	public Resolution handleValidationErrors(ValidationErrors errors) {
		return new ForwardResolution(VIEW);
	}

	@HandlesEvent("save")
	public Resolution save() {
		Resolution result = new ForwardResolution(VIEW);
		try {
			if (answer != null) {
				answer.setAnswers(answers);
				Map<String, Boolean> m = new HashMap<String, Boolean>();
				boolean duplicate = false;
				boolean allAnswered = true;
				for (int i = 0; i < questionsSize; i++) {
					if (answers.size() < questionsSize) {
						allAnswered = false;
						break;
					}
					String a = answers.get(i);
					if (a == null || a.trim().length() == 0) {
						allAnswered = false;
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
					addGlobalActionError("answerAddEdit.pleaseMakeAllAnswersUnique");
				} else if (allAnswered == false) {
					addGlobalActionError("answerAddEdit.pleaseAnswerAllQuestions");
				} else if (answer != null) {
					_svcGame.saveAnswer(answer);
					addGlobalActionMessage("answerEdit.answersUpdated");
					log.info("updated answers:" + answer);
					result = new ForwardResolution(GameActionBean.class);
				}
			}
		} catch (WPServiceException e) {
			addGlobalActionError("answerEdit.unableToSaveAnswers");
		}
		return result;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public String getAnswerKeyStringEncrypted() {
		return answerKeyStringEncrypted;
	}

	public void setAnswerKeyStringEncrypted(String a) {
		this.answerKeyStringEncrypted = a;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public List<String> getQuestions() {
		return questions;
	}
}
