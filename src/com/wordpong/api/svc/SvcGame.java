package com.wordpong.api.svc;

import java.util.List;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.GameMyTurn;

public interface SvcGame {

    List<GameMyTurn> getMyTurns();

    void setMyTurns(List<GameMyTurn> myTurns);

    void inviteFriends(User user, List<String> emails) throws WPServiceException;

    List<FriendInvite> getFriendInvites(User user) throws WPServiceException;

    void cancelInvitation(User user, String email) throws WPServiceException ;

    Question saveQuestion(Question u) throws WPServiceException;
}
