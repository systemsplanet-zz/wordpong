package com.wordpong.app.action.game;

import java.util.List;

import javax.annotation.security.PermitAll;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.model.InviteGame;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.GameTheirTurn;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
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
	@HandlesEvent("processFriendInvite")
	public Resolution processFriendInvite() {
		return new ForwardResolution(FriendInviteAcceptActionBean.class);
	}

	@DontValidate
	@HandlesEvent("processGameInvite")
	public Resolution processGameInvite() {
		return new ForwardResolution(GameInviteActionBean.class);
	}

	public User getUser() {
		return user;
	}

	public List<InviteFriend> getInviteFriends() throws WPServiceException {
		user = getContext().getUserFromSession();
		return _svcGame.getInviteFriends(user);
	}

	public List<InviteGame> getInviteGames() throws WPServiceException {
		user = getContext().getUserFromSession();
		return _svcGame.getInviteGames(user);
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