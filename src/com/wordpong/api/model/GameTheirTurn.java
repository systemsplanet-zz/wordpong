package com.wordpong.api.model;


public class GameTheirTurn extends Game {

	private static final long serialVersionUID = 1L;

	public enum Action {
		Unknown, InvitationSent, AnswerQuestionRequestSent
	}

}
