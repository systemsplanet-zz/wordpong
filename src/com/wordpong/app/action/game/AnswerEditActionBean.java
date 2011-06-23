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
import com.wordpong.api.model.Question;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class AnswerEditActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(AnswerEditActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_answerEdit.jsp";

	private SvcGame _svcGame;
	// when the user selects answers to edit these are populated
	private String answerKeyString;
	private String questionDescription;
	private Answer answer;
	private List<String> answers = new ArrayList<String>();

	private List<String> questions;
	private int questionsSize = 0;

	public AnswerEditActionBean() {
		_svcGame = SvcGameFactory.getGameService();
	}

	@DontValidate
	public Resolution back() {
		return new RedirectResolution(AnswerListActionBean.class);
	}

	private void loadAnswer() {
		if (answer == null) {
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

	@DontValidate
	@DefaultHandler
	public Resolution view() {
		loadAnswer();

		// TODO: read the questions/answers if not loaded
		log.info("answerKeyString:" + answerKeyString + " desc:"
				+ questionDescription);
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

	@HandlesEvent("save")
	public Resolution save() {
		Resolution result = new ForwardResolution(VIEW);
		try {
			loadAnswer();
			if (answer != null) {
				answer.setAnswers(answers);

				boolean allAnswered = true;
				for (int i = 0; i < questionsSize; i++) {
					if (answers.size() < questionsSize
							|| answers.get(i) == null
							|| answers.get(i).trim().length() == 0) {
						allAnswered = false;
						break;
					}
				}
				if (allAnswered && answer != null) {
					_svcGame.saveAnswer(answer);
					addGlobalActionMessage("answerEdit.answersUpdated");
					log.info("updated answers:" + answer);
					result = new ForwardResolution(GameActionBean.class);
				} else {
					addGlobalActionError("answerAddEdit.pleaseAnswerAllQuestions");
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

	public String getAnswerKeyString() {
		return answerKeyString;
	}

	public void setAnswerKeyString(String answerKeyString) {
		this.answerKeyString = answerKeyString;
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
