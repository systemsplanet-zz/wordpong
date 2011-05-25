package com.wordpong.app.action.game;

import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class GameInviteAnswersConfirmActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(GameInviteAnswersConfirmActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_gameInviteAnswersConfirm.jsp";

	private String answerKeyString;
	private String questionDescription = "??";

	public GameInviteAnswersConfirmActionBean() {
	}

	@DontValidate
	public Resolution cancel() {
		return new ForwardResolution(GameActionBean.class);
	}

	@DontValidate
	@DefaultHandler
	public Resolution view() {
		return new ForwardResolution(VIEW);
	}

	@HandlesEvent("startGame")
	public Resolution startGame() {
		AppActionBeanContext c = getContext();
		if (c != null) {
			try {
				User user = c.getUserFromSession();
				if (user != null) {
					// todo: START GAME . prompt user to select game to send
					SvcGame sg = SvcGameFactory.getGameService();
					SvcUser su = SvcUserFactory.getUserService();
			} else {
					// session expire?
				}
			} catch (Exception e) {
				log.warning("unable to start game");
			}
		}
		// redirect back here
		return new ForwardResolution(VIEW);
	}

	// on errors, only reply with the content, not the entire page
	public Resolution handleValidationErrors(ValidationErrors errors) {
		return new ForwardResolution(VIEW);
	}


	public String getAnswerKeyString() {
		return answerKeyString;
	}

	public void setAnswerKeyString(String key) {
		this.answerKeyString = key;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}
	

}
