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

import com.wordpong.api.model.Answer;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class AnswerListActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(AnswerListActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_answerList.jsp";

	private SvcGame _svcGame;
	private User user;

	// when the user selects answers to edit this is populated
	private String questionDescription;

	public AnswerListActionBean() {
		_svcGame = SvcGameFactory.getSvcGame();
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

	@HandlesEvent("addAnswer")
	public Resolution addAnswer() {
		return new ForwardResolution(AnswerAddActionBean.class);
	}

	@HandlesEvent("editAnswers")
	public Resolution editAnswers() {
		Resolution resolution = new ForwardResolution(VIEW);
		AppActionBeanContext c = getContext();
		if (c != null) {
			try {
				user = c.getUserFromSession();
				if (user != null) {
					log.info("edit answer quest:" + questionDescription);
					resolution = new ForwardResolution(
							AnswerEditActionBean.class);
				} else {
					// session expire?
				}
			} catch (Exception e) {
				addGlobalActionError("unableToEditAnswers");
				log.warning("unable to edit answers");
			}
		}
		// redirect back here
		return resolution;
	}

	@ValidationMethod
	public void validateUser(ValidationErrors errors) {
		AppActionBeanContext c = getContext();
		if (c != null) {
			// TODO: validate
		}
	}

	// on errors, only reply with the content, not the entire page
	public Resolution handleValidationErrors(ValidationErrors errors) {
		return new ForwardResolution(VIEW);
	}

	public List<Answer> getAnswers() {
		List<Answer> result = new ArrayList<Answer>();
		user = getContext().getUserFromSession();
		log.info("user:" + user + " svc:" + _svcGame);
		try {
			// get the list of answers for this user
			result = _svcGame.getAnswers(user);
		} catch (WPServiceException e) {
			log.warning("getAnswers err:" + e.getMessage());
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
