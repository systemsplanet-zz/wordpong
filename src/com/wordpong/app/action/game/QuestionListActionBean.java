package com.wordpong.app.action.game;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class QuestionListActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(QuestionListActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_questionList.jsp";

	private SvcGame _svcGame;
	private User user;

	// when the user selects questions to edit this is populated
	private String questionDescription;

	public QuestionListActionBean() {
		_svcGame = SvcGameFactory.getGameService();
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
					log.info("edit question quest:" + questionDescription);
					resolution = new ForwardResolution(
							QuestionEditActionBean.class);
				} else {
					// session expire?
				}
			} catch (Exception e) {
				addGlobalActionError("unableToEditQuestions");
				log.warning("unable to edit questions");
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
		log.info("user:" + user + " svc:" + _svcGame);
		try {
			// get the list of questions for this user
			result = _svcGame.getMyQuestions(user);
		} catch (WPServiceException e) {
			log.warning("getQuestions err:" + e.getMessage());
		}
		return result;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

}
