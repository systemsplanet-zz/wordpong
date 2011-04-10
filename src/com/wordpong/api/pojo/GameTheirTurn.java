package com.wordpong.api.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GameTheirTurn {

    private static final long serialVersionUID = 1L;

    public static enum Action {
        Unknown, InvitationSent, AnswerQuestionRequestSent
    }

    private Action _action = Action.Unknown;

    private String createdAtString = "?";
    private String id = "?";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAtString() {
        return createdAtString;
    }

    public void setCreatedAtString(String createdAtString) {
        this.createdAtString = createdAtString;
    }

    public Action getAction() {
        return _action;
    }

    public void setAction(Action action) {
        this._action = action;
    }

    public String getActionString() {
        String result = "Unknown";
        if (_action != null) {
            switch (_action) {
            case InvitationSent:
                result = "Invitation Sent";
                break;
            case AnswerQuestionRequestSent:
                result = "Answer Question Request Sent";
                break;
            case Unknown:
                result = "Unknown";
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
