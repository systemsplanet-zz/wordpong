package com.wordpong.api.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class GameTheirTurn extends Game {

	private static final long serialVersionUID = 1L;

	public enum Action {
		Unknown, InvitationSent, AnswerQuestionRequestSent
	}
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
