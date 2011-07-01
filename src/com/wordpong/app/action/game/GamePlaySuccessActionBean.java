package com.wordpong.app.action.game;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

import com.wordpong.app.action.BaseActionBean;

public class GamePlaySuccessActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final String VIEW = "/WEB-INF/jsp/game/_gamePlaySuccess.jsp";

	public GamePlaySuccessActionBean() {
	}

	@DontValidate
	@DefaultHandler
	public Resolution view() {
		return new ForwardResolution(VIEW);
	}

	@DontValidate
	public Resolution done() {
		return new ForwardResolution(GameActionBean.class);
	}

	// on errors, only reply with the content, not the entire page
	public Resolution handleValidationErrors(ValidationErrors errors) {
		return new ForwardResolution(VIEW);
	}

}
