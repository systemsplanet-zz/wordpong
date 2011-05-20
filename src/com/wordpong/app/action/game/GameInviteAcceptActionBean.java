package com.wordpong.app.action.game;

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
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class GameInviteAcceptActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(GameInviteAcceptActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_gameInviteAccept.jsp";

	@Validate(required = true, converter = EmailTypeConverter.class, minlength = 4, maxlength = 50)
	private String email;
	private String key;
	private String createdAtString = "??";

	public GameInviteAcceptActionBean() {
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

	@HandlesEvent("ignoreInvite")
	public Resolution ignoreInvite() {
		try {
			// hide game invite from invitee
			SvcGame sg = SvcGameFactory.getGameService();
			sg.ignoreGameInvitation(key);
			addGlobalActionError("gameInviteAccept.inviteIgnored");
		} catch (WPServiceException e) {
			addGlobalActionError("gameInviteAccept.unableToIgnore");
		}
		return new ForwardResolution(VIEW);
	}

	@HandlesEvent("acceptInviteConfirm")
	public Resolution acceptInvite() {
		AppActionBeanContext c = getContext();
		if (c != null) {
			try {
				User user = c.getUserFromSession();
				if (user != null) {
					// todo: START GAME
					SvcGame sg = SvcGameFactory.getGameService();
					SvcUser su = SvcUserFactory.getUserService();
					// user = su.getByKey(user);
					addGlobalActionError("gameInviteAccept.inviteAccepted");
				} else {
					// session expire?
				}
			} catch (Exception e) {
				addGlobalActionError("gameInviteAccept.unableToAccept");
				log.warning("unable to accept invite");
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
