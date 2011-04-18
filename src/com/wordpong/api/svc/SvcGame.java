package com.wordpong.api.svc;

import java.util.List;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.GameMyTurn;
import com.wordpong.api.pojo.GameTheirTurn;

public interface SvcGame {

    List<GameMyTurn> getMyTurns(User user);

    public List<GameTheirTurn> getTheirTurns(User user);

    Question saveQuestion(Question u) throws WPServiceException;

    void inviteFriends(User user, List<String> emails) throws WPServiceException;

    List<FriendInvite> getFriendInvitesByInviterKey(User user) throws WPServiceException;

    void updateFriendInvites(User user) throws WPServiceException;

    void updateFriendInvites();// background task

    void cancelInvitation(User user, String email) throws WPServiceException;

    void ignoreInvitation(String key) throws WPServiceException;

}
