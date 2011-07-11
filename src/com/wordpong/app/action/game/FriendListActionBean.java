package com.wordpong.app.action.game;

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
import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class FriendListActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger
			.getLogger(FriendListActionBean.class.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_friendList.jsp";

	private SvcGame _svcGame;

	private User user;

	public FriendListActionBean() {
		_svcGame = SvcGameFactory.getGameService();
	}

	@DontValidate
	public Resolution back() {
		return new ForwardResolution(GameActionBean.class);
	}

	@DontValidate
	public Resolution friendInvite() {
		return new ForwardResolution(FriendInviteActionBean.class);
	}

	@DontValidate
	@DefaultHandler
	public Resolution view() {
		return new ForwardResolution(VIEW);
	}

	@HandlesEvent("selectFriend")
	public Resolution selectFriend() {
		// TODO:
		log.info("selected friend");
		return new ForwardResolution(VIEW);
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

	public List<User> getMyFriends() throws WPServiceException {
		user = getContext().getUserFromSession();
		long start = System.currentTimeMillis();
		List<User> result = _svcGame.getMyFriendsGames(user);
		log.info("getMyFriends elapsedMs:"
				+ (System.currentTimeMillis() - start));
		return result;
	}
}
