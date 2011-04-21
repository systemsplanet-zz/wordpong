package com.wordpong.api.pojo;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.wordpong.api.model.Game;
import com.wordpong.api.model.User;

public class FriendGames {

    private User friend;
    private List<Game> games;

    public String getId() {
        String result = friend.getKey().toString();
        return result;
    }

    public String getFriendInfo() {
        String result = "unknown";
        if (friend != null) {
            result = friend.getFullName();
        }
        return result;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
