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
import net.sourceforge.stripes.validation.ValidationMethod;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.InviteGame;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.dao.DaoInviteGame;
import com.wordpong.api.svc.dao.DaoInviteGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class TheirTurnGameInviteActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(TheirTurnGameInviteActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_theirTurnGameInvite.jsp";

	private String inviteGameKeyStringEncrypted;
	private String inviteGameKeyString;
	private InviteGame inviteGame;

	public TheirTurnGameInviteActionBean() {
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

	@HandlesEvent("withdrawInvitation")
	public Resolution withdrawInvitation() {
		Resolution result = new ForwardResolution(VIEW);
		AppActionBeanContext c = getContext();
		if (c != null) {
			try {
				DaoInviteGame dig = DaoInviteGameFactory.getInviteGameDao();
				dig.withdrawInvitation(inviteGameKeyString);
				addGlobalActionMessage("theirTurnGameInvite.cancelledInvite");
				result = new ForwardResolution(GameActionBean.class);
			} catch (Exception e) {
				addGlobalActionError("theirTurnGameInvite.unableToCancelInvite");
				log.warning("unable to uninvite game");
			}
		}
		// redirect back here
		return result;
	}

	@ValidationMethod
	public void validateUser(ValidationErrors errors) {
		AppActionBeanContext c = getContext();
		if (c != null) {
		}
	}

	// on errors, only reply with the content, not the entire page
	public Resolution handleValidationErrors(ValidationErrors errors) {
		return new ForwardResolution(VIEW);
	}

	public InviteGame getInviteGame() {
		return inviteGame;
	}

	public void setInviteGame(InviteGame inviteGame) {
		this.inviteGame = inviteGame;
	}

	public String getInviteGameKeyStringEncrypted() {
		return inviteGameKeyStringEncrypted;
	}

	public void setInviteGameKeyStringEncrypted(
			String inviteGameKeyStringEncrypted) {
		this.inviteGameKeyStringEncrypted = inviteGameKeyStringEncrypted;
	}
}
