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

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class GameInviteAnswersActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(GameInviteAnswersActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_gameInviteAnswers.jsp";

	private String answerKeyString;
	private String key; //inviterUserKeyString

	private String questionDescription = "??";

	public GameInviteAnswersActionBean() {
	}

	@DontValidate
	public Resolution back() {
		return new ForwardResolution(GameInviteActionBean.class);
	}

	@DontValidate
	@DefaultHandler
	public Resolution view() {
		return new ForwardResolution(VIEW);
	}

	public List<Answer> getAnswers() {
		List<Answer> result = new ArrayList<Answer>();
		SvcGame sg = SvcGameFactory.getGameService();
		User user = getContext().getUserFromSession();
		if (user != null) {
			try {
				result = sg.getAnswers(user);
			} catch (WPServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@HandlesEvent("selectAnswers")
	public Resolution selectAnswers() {
		Resolution result = new ForwardResolution(VIEW);
		AppActionBeanContext c = getContext();
		if (c != null) {
			try {
				result = new ForwardResolution(
						GameInviteAnswersConfirmActionBean.class);
			} catch (Exception e) {
				addGlobalActionError("gameInviteQuestions.unableToAccept");
				log.warning("unable to select questions");
			}
		}
		// redirect back here
		return result;
	}

	// on errors, only reply with the content, not the entire page
	public Resolution handleValidationErrors(ValidationErrors errors) {
		return new ForwardResolution(VIEW);
	}

	public String getAnswerKeyString() {
		return answerKeyString;
	}

	public void setAnswerKeyString(String k) {
		this.answerKeyString = k;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public String getKey() {
		return key;
	}

	public void setKet(String k) {
		this.key = k;
	}

}
