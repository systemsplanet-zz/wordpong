package com.wordpong.app.action.game;

import java.util.ArrayList;
import java.util.List;
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
import com.wordpong.api.model.Answer;
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
	private static final String ANSWERS = "/WEB-INF/jsp/game/_gameInvite_Answers.jsp";
	private static final String CONFIRM = "/WEB-INF/jsp/game/_gameInvite_Confirm.jsp";

	private String inviteGameKeyStringEncrypted;
	private String inviteGameKeyString;

	private String answerKeyStringEncrypted;
	private String answerKeyString;

	private InviteGame inviteGame;

	private Answer answer;

	public GameInviteActionBean() {
	}

	@After(stages = LifecycleStage.BindingAndValidation)
	public void doPostValidationStuff() {
		SvcGame sg = SvcGameFactory.getGameService();
		if (inviteGameKeyStringEncrypted != null) {
			inviteGameKeyString = CryptoUtil
					.decrypt(inviteGameKeyStringEncrypted);
			try {
				inviteGame = sg.getInviteGame(inviteGameKeyString);
			} catch (WPServiceException e) {
			}
		}
		if (answerKeyStringEncrypted != null) {
			answerKeyString = CryptoUtil.decrypt(answerKeyStringEncrypted);
			try {
				answer = sg.getAnswer(answerKeyString);
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
			addGlobalActionMessage("gameInvite.inviteIgnored");
		} catch (WPServiceException e) {
			addGlobalActionError("gameInvite.unableToIgnore");
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
					result = new ForwardResolution(ANSWERS);
				} else {
					// session expire?
				}
			} catch (Exception e) {
				addGlobalActionError("gameInvite.unableToAccept");
				log.warning("unable to accept invite");
			}
		}
		// redirect back here
		return result;
	}

	/*
	 * ANSWERS FOLLOW
	 */

	public List<Answer> getAnswers() {
		List<Answer> result = new ArrayList<Answer>();
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
		Resolution result = new ForwardResolution(ANSWERS);
		AppActionBeanContext c = getContext();
		if (c != null) {
			try {
				result = new ForwardResolution(CONFIRM);
			} catch (Exception e) {
				addGlobalActionError("gameInvite.unableToAccept");
				log.warning("unable to select questions");
			}
		}
		// redirect back here
		return result;
	}

	/*
	 * CONFIRM FOLLOWS
	 */

	@HandlesEvent("startGame")
	public Resolution startGame() {
		SvcGame sg = SvcGameFactory.getGameService();
		try {
			sg.createGame(inviteGame, answer);
			addGlobalActionMessage("gameInvite.gameSent");
		} catch (Exception e) {
			addGlobalActionError("gameInvite.unableToStartGame");
			log.warning("unable to start game");
		}
		// redirect home
		return new ForwardResolution(GameActionBean.class);
	}

	/*
	 * BIOLDERPLATE FOLLOW
	 */

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

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public String getAnswerKeyStringEncrypted() {
		return answerKeyStringEncrypted;
	}

	public void setAnswerKeyStringEncrypted(String answerKeyStringEncrypted) {
		this.answerKeyStringEncrypted = answerKeyStringEncrypted;
	}

}
