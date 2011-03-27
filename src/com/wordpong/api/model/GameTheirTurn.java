package com.wordpong.api.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GameTheirTurn {

    private static final long serialVersionUID = 1L;

    public enum Action {
        Unknown, InvitationSent, AnswerQuestionRequestSent
    }

    private String id = "123";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
