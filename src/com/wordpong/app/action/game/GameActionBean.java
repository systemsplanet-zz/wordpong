package com.wordpong.app.action.game;

import java.util.List;
//import java.util.logging.Logger;

import javax.annotation.security.PermitAll;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;

import com.wordpong.api.model.User;
import com.wordpong.api.pojo.GameMyTurn;
import com.wordpong.api.pojo.GameTheirTurn;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.util.SvcLocale;
import com.wordpong.app.action.BaseActionBean;
import com.wordpong.app.stripes.AppActionBeanContext;

public class GameActionBean extends BaseActionBean {
	// private static final Logger log =
	// Logger.getLogger(GameActionBean.class.getName());
	public static final String VIEW = "/WEB-INF/jsp/game/index.jsp";

	private SvcGame _svcGame;
	private User user;
	private String details; // button caption details

	public GameActionBean() {
		_svcGame = SvcGameFactory.getGameService();
	}

	// Make sure user is authenticated
	@Before(stages = { LifecycleStage.BindingAndValidation })
	public Resolution authorizeFilter() {
		return assertAuthenticated();
	}

	// Requires authenticated user
	@PermitAll
	@DefaultHandler
	public Resolution showMe() {
		AppActionBeanContext c = getContext();
		if (user == null) {
			user = c.getUserFromSession();
		}
		return new ForwardResolution(VIEW);
	}

	@DontValidate
	public Resolution theirTurnSelect() {
		// TODO
		return new ForwardResolution(ProfileEditActionBean.class);
	}

	@DontValidate
	public Resolution myTurnSelect() {
		// TODO
		return new ForwardResolution(ProfileEditActionBean.class);
	}

	@DontValidate
	public Resolution profileEdit() {
		return new ForwardResolution(ProfileEditActionBean.class);
	}

	@DontValidate
	public Resolution friendList() {
		return new ForwardResolution(FriendListActionBean.class);
	}

	@DontValidate
	public Resolution answerList() {
		return new ForwardResolution(AnswerListActionBean.class);
	}

	@DontValidate
	@HandlesEvent("processMyTurnSelection")
	public Resolution processMyTurnSelection() {
		Resolution result;
		String newFriend = SvcLocale.get("newFriend");
		if (details != null && details.startsWith(newFriend)) {
			result = new ForwardResolution(FriendInviteAcceptActionBean.class);
		} else {
			result = new ForwardResolution(GameInviteActionBean.class);
		}
		return result;
	}

	public User getUser() {
		return user;
	}

	// get list of things for me to do, like:
	// respond to friend/game invites
	public List<GameMyTurn> getMyTurns() {
		user = getContext().getUserFromSession();
		return _svcGame.getMyTurns(user);
	}

	public List<GameTheirTurn> getTheirTurns() {
		user = getContext().getUserFromSession();
		return _svcGame.getTheirTurns(user);
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}