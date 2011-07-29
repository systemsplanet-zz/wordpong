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
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.Game;
import com.wordpong.api.model.Question;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.app.action.BaseActionBean;

public class GamePlayActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final Logger log = Logger.getLogger(GamePlayActionBean.class
			.getName());
	private static final String VIEW = "/WEB-INF/jsp/game/_gamePlay.jsp";
	private static final String SUCCESS = "/WEB-INF/jsp/game/_gamePlaySuccess.jsp";

	private String gameKeyStringEncrypted;
	private String gameKeyString;
	Question question;
	private Answer answer;
	private Game game;

	public GamePlayActionBean() {
	}

	@After(stages = LifecycleStage.BindingAndValidation)
	public void doPostValidationStuff() {
		if (gameKeyStringEncrypted != null) {
			gameKeyString = CryptoUtil.decrypt(gameKeyStringEncrypted);
			SvcGame sg = SvcGameFactory.getSvcGame();
			try {
				game = sg.getGame(gameKeyString);
				String answerKeyString = game.getAnswersKeyString();
				answer = sg.getAnswer(answerKeyString);
				String questionKeyString = answer.getQuestionKeyString();
				question = sg.getQuestion(questionKeyString);
			} catch (WPServiceException e) {
				log.warning("doPostValidationStuff: err" + e.getMessage());
			}
		}
	}

	@DontValidate
	public Resolution back() {
		return new ForwardResolution(GameActionBean.class);
	}

	@DontValidate
	@HandlesEvent("success")
	public Resolution success() {
		// Mark Game as played
		SvcGame sg = SvcGameFactory.getSvcGame();
		try {
			sg.finishGame(gameKeyString);
		} catch (WPServiceException e) {
			log.warning("finish game: err" + e.getMessage());
		}
		return new ForwardResolution(SUCCESS);
	}

	@DontValidate
	@DefaultHandler
	public Resolution view() {
		return new ForwardResolution(VIEW);
	}

	@HandlesEvent("match")
	public Resolution match() {
		Resolution result = new ForwardResolution(VIEW);
		return result;
	}

	// on errors, only reply with the content, not the entire page
	public Resolution handleValidationErrors(ValidationErrors errors) {
		return new ForwardResolution(VIEW);
	}

	public String getGameKeyStringEncrypted() {
		return gameKeyStringEncrypted;
	}

	public void setGameKeyStringEncrypted(String gameKeyStringEncrypted) {
		this.gameKeyStringEncrypted = gameKeyStringEncrypted;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

}
