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
import com.wordpong.api.model.Game;
import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.model.InviteGame;
import com.wordpong.api.model.User;
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
	@HandlesEvent("addGame")
	public Resolution addGame() {
		// TODO: make add game, not add friend
		return new ForwardResolution(FriendInviteActionBean.class);
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

	@DontValidate
	@HandlesEvent("viewTheirTurnGameInvite")
	public Resolution viewTheirTurnGameInvite() {
		return new ForwardResolution(TheirTurnGameInviteActionBean.class);
	}

	@DontValidate
	@HandlesEvent("viewTheirTurnFriendInvite")
	public Resolution viewTheirTurnFriendInvite() {
		return new ForwardResolution(TheirTurnFriendInviteActionBean.class);
	}

	@DontValidate
	@HandlesEvent("playGame")
	public Resolution playGame() {
		// TODO: select different game presentations modes here 
		return new ForwardResolution(GamePlayActionBean.class);
	}

	public User getUser() {
		return user;
	}

	public List<InviteFriend> getMyTurnInviteFriends()
			throws WPServiceException {
		user = getContext().getUserFromSession();
		return _svcGame.getMyTurnInviteFriends(user);
	}

	public List<InviteGame> getMyTurnInviteGames() throws WPServiceException {
		user = getContext().getUserFromSession();
		return _svcGame.getMyTurnInviteGames(user);
	}

	public List<Game> getMyTurnGames() throws WPServiceException {
		user = getContext().getUserFromSession();
		return _svcGame.getMyTurnGames(user);
	}

	public List<InviteFriend> getTheirTurnsInviteFriend() {
		user = getContext().getUserFromSession();
		return _svcGame.getTheirTurnsInviteFriend(user);
	}

	public List<InviteGame> getTheirTurnsInviteGame() {
		user = getContext().getUserFromSession();
		return _svcGame.getTheirTurnsInviteGame(user);
	}


}