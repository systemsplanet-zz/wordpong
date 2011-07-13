package com.wordpong.api.svc;

import java.util.List;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.Game;
import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;

public interface SvcGame {

    List<InviteFriend> getMyTurnInviteFriends(User user) throws WPServiceException;

    Game getGame(String gameKeyStr) throws WPServiceException;

    List<Game> getMyTurnGames(User user) throws WPServiceException;

    List<InviteFriend> getTheirTurnsInviteFriend(User user) throws WPServiceException;

    InviteFriend getInviteFriend(String inviteFriendKeyStr) throws WPServiceException;

    void inviteFriends(User user, List<String> emails) throws WPServiceException;

    List<InviteFriend> getFriendInvitesByInviterKey(User user) throws WPServiceException;

    void updateFriendInvites(User user) throws WPServiceException;

    void updateFriendInvites();// background task

    void cancelFriendInvitation(User user, String email) throws WPServiceException;

    void ignoreFriendInvitation(String key) throws WPServiceException;

    void createGame(Game g, User u) throws WPServiceException;

    List<Game> getTheirTurnsGame(User user) throws WPServiceException;

    void finishGame(String gameKeyString) throws WPServiceException;

    void makeFriends(String friendInviteKeyStr) throws WPServiceException;

    User getUser(User u) throws WPServiceException;

    List<User> getMyFriends(User u) throws WPServiceException;

    List<User> getMyFriendsGames(User u) throws WPServiceException;

    void seedQuestions(User user) throws WPServiceException;

    Question saveQuestion(Question u) throws WPServiceException;

    Question getQuestion(String questionKey) throws WPServiceException;

    List<Question> getUnansweredQuestions(User user) throws WPServiceException;

    Answer saveAnswer(Answer u) throws WPServiceException;

    List<Answer> getAnswers(User u) throws WPServiceException;

    Answer getAnswer(String answerKey) throws WPServiceException;
}
