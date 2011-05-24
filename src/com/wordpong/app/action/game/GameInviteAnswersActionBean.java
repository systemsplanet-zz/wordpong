package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class GameInviteAnswersActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(GameInviteAnswersActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_gameInviteAnswers.jsp";

	@Validate(required = true, converter = EmailTypeConverter.class, minlength = 4, maxlength = 50)
	private String email;
	private String key;
	private String createdAtString = "??";

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
		List<Answer> result= new ArrayList<Answer>();
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
		AppActionBeanContext c = getContext();
		if (c != null) {
			try {
				User user = c.getUserFromSession();
				if (user != null) {
					// todo: START GAME . prompt user to select game to send
					SvcGame sg = SvcGameFactory.getGameService();
					SvcUser su = SvcUserFactory.getUserService();
					// user = su.getByKey(user);
					addGlobalActionError("gameInviteQuestions.inviteAccepted");
				} else {
					// session expire?
				}
			} catch (Exception e) {
				addGlobalActionError("gameInviteQuestions.unableToAccept");
				log.warning("unable to select questions");
			}
		}
		// redirect back here
		return new ForwardResolution(VIEW);
	}

	// on errors, only reply with the content, not the entire page
	public Resolution handleValidationErrors(ValidationErrors errors) {
		return new ForwardResolution(VIEW);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String e) {
		if (e != null) {
			e = e.trim().toLowerCase();
		}
		email = e;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCreatedAtString() {
		return createdAtString;
	}

	public void setCreatedAtString(String createdAtString) {
		this.createdAtString = createdAtString;
	}
}
