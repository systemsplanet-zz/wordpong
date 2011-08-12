package com.wordpong.app.action.game;

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

import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.dao.DaoInviteFriend;
import com.wordpong.api.svc.dao.DaoInviteFriendFactory;
import com.wordpong.api.svc.err.WPServiceException;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;
import com.wordpong.util.debug.LogUtil;

public class TheirTurnFriendInviteActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final String VIEW = "/WEB-INF/jsp/game/_theirTurnFriendInvite.jsp";

	private String inviteFriendKeyStringEncrypted;
	private String inviteFriendKeyString;
	private InviteFriend inviteFriend;

	public TheirTurnFriendInviteActionBean() {
	}

	@After(stages = LifecycleStage.BindingAndValidation)
	public void doPostValidationStuff() {
		if (inviteFriendKeyStringEncrypted != null) {
			inviteFriendKeyString = CryptoUtil
					.decrypt(inviteFriendKeyStringEncrypted);
			SvcGame sg = SvcGameFactory.getSvcGame();
			try {
				inviteFriend = sg.getInviteFriend(inviteFriendKeyString);
			} catch (WPServiceException e) {
                LogUtil.logException("doPostValidationStuff", e);
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

	@HandlesEvent("cancelInvite")
	public Resolution cancelInvite() {
		Resolution result = new ForwardResolution(VIEW);
		AppActionBeanContext c = getContext();
		if (c != null) {
			try {
				DaoInviteFriend dif = DaoInviteFriendFactory
						.getDaoInviteFriend();
				dif.withdrawInvitation(inviteFriendKeyString);
				addGlobalActionMessage("theirTurnFriendInvite.cancelledInvite");
				result = new ForwardResolution(GameActionBean.class);
			} catch (Exception e) {
				addGlobalActionError("theirTurnFriendInvite.unableToCancelInvite");
                LogUtil.logException("cancelInvite", e);
			}
		}
		return result;
	}

	@ValidationMethod
	public void validateUser(ValidationErrors errors) {
	}

	// on errors, only reply with the content, not the entire page
	public Resolution handleValidationErrors(ValidationErrors errors) {
		return new ForwardResolution(VIEW);
	}

	public InviteFriend getInviteFriend() {
		return inviteFriend;
	}

	public void setInviteFriend(InviteFriend inviteFriend) {
		this.inviteFriend = inviteFriend;
	}

	public String getInviteFriendKeyStringEncrypted() {
		return inviteFriendKeyStringEncrypted;
	}

	public void setInviteFriendKeyStringEncrypted(
			String inviteFriendKeyStringEncrypted) {
		this.inviteFriendKeyStringEncrypted = inviteFriendKeyStringEncrypted;
	}
}
