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

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class AnswerAddEditActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(AnswerAddEditActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_answerAddEdit.jsp";

	private SvcGame _svcGame;
	private String questionKeyStringEncrypted;
	private String questionKeyString;
	private String questionDescription;
	private List<String> questions;
	private int questionsSize = 0;
	private List<String> answers = new ArrayList<String>();

	public AnswerAddEditActionBean() {
		_svcGame = SvcGameFactory.getSvcGame();
	}

	@After(stages = LifecycleStage.BindingAndValidation)
	public void doPostValidationStuff() {
		if (questionKeyStringEncrypted != null) {
			questionKeyString = CryptoUtil.decrypt(questionKeyStringEncrypted);
			if (questions == null && questionKeyString != null) {
				// todo read questionKeyString question
				try {
					Question q = _svcGame.getQuestion(questionKeyString);
					// create a QuestionEdit
					if (q != null) {
						questions = q.getQuestions();
						questionsSize = questions.size();
					}
				} catch (WPServiceException e) {
					log.warning("doPostValidationStuff error:" + e.getMessage());
				}
			}
		}
	}

	@DontValidate
	public Resolution back() {
		return new RedirectResolution(AnswerAddActionBean.class);
	}

	@DontValidate
	@DefaultHandler
	public Resolution view() {
		getQuestions();
		if (answers.size() == 0) {
			for (int i = 0; i < questionsSize; i++) {
				answers.add("");
			}
		}
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

	public List<String> getQuestions() {
		return questions;
	}

	@HandlesEvent("save")
	public Resolution save() {
		Resolution result = new ForwardResolution(VIEW);
		getQuestions();
		boolean allAnswered = true;
		boolean duplicate = false;
		Map<String, Boolean> m = new HashMap<String, Boolean>();
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
		} else {
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
				addGlobalActionMessage("answerAddEdit.answersUpdated");
				log.info("updated answers:" + answers);
				result = new ForwardResolution(GameActionBean.class);
			} catch (WPServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
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

	public void setQuestionKeyStringEncrypted(String q) {
		this.questionKeyStringEncrypted = q;
	}
}
