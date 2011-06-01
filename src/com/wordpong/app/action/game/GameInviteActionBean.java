package com.wordpong.app.action.game;

import java.util.logging.Logger;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.util.CryptoUtil;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.InviteGame;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class GameInviteActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(GameInviteActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_gameInvite.jsp";

	private String inviteGameKeyStringEncrypted;
	private String inviteGameKeyString;
	private InviteGame inviteGame;

	public GameInviteActionBean() {
	}

	@After(stages = LifecycleStage.BindingAndValidation)
	public void doPostValidationStuff() {
		if (inviteGameKeyStringEncrypted != null) {
			inviteGameKeyString = CryptoUtil
					.decrypt(inviteGameKeyStringEncrypted);
			SvcGame sg = SvcGameFactory.getGameService();
			try {
				inviteGame = sg.getInviteGame(inviteGameKeyString);
			} catch (WPServiceException e) {
			}
		}
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
			sg.ignoreGameInvitation(inviteGameKeyString);
			addGlobalActionMessage("gameInviteAccept.inviteIgnored");
		} catch (WPServiceException e) {
			addGlobalActionError("gameInviteAccept.unableToIgnore");
		}
		return new ForwardResolution(GameActionBean.class);
	}

	@HandlesEvent("acceptInviteConfirm")
	public Resolution acceptInvite() {
		Resolution result = new ForwardResolution(VIEW);
		AppActionBeanContext c = getContext();
		if (c != null) {
			try {
				User user = c.getUserFromSession(); // todo: required?
				if (user != null) {
					result = new ForwardResolution(
							GameInviteAnswersActionBean.class);
				} else {
					// session expire?
				}
			} catch (Exception e) {
				addGlobalActionError("gameInviteAccept.unableToAccept");
				log.warning("unable to accept invite");
			}
		}
		// redirect back here
		return result;
	}

	// on errors, only reply with the content, not the entire page
	public Resolution handleValidationErrors(ValidationErrors errors) {
		return new ForwardResolution(VIEW);
	}

	public String getInviteGameKeyStringEncrypted() {
		return inviteGameKeyStringEncrypted;
	}

	public void setInviteGameKeyStringEncrypted(
			String inviteGameKeyStringEncrypted) {
		this.inviteGameKeyStringEncrypted = inviteGameKeyStringEncrypted;
	}

	public InviteGame getInviteGame() {
		return inviteGame;
	}

	public void setInviteGame(InviteGame inviteGame) {
		this.inviteGame = inviteGame;
	}

}
