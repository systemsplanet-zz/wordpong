package com.wordpong.api.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GameMyTurn {

    private static final long serialVersionUID = 1L;

    private String id = "unknown";

    public static enum Action {
        Unknown, InvitationRequest, InviteAccepted, AnswerQuestion
    }

    private Action _action = Action.Unknown;

    public Action getAction() {
        return _action;
    }

    public String getActionString() {
        String result = "Unknown";
        if (_action != null) {
            switch (_action) {
            case InvitationRequest:
                result = "Invitation Request";
                break;
            case InviteAccepted:
                result = "Invite Accepted";
                break;
            case AnswerQuestion:
                result = "Answer Question";
                break;
            case Unknown:
                result = "Unknown";
                break;
            }
        }
        return result;
    }

    public void setAction(Action action) {
        _action = action;
    }

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
