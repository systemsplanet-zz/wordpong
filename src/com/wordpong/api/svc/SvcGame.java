package com.wordpong.api.svc;

import java.util.List;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.Game;
import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.model.InviteGame;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;

public interface SvcGame {

	List<InviteFriend> getMyTurnInviteFriends(User user)
			throws WPServiceException;

	List<InviteGame> getMyTurnInviteGames(User user) throws WPServiceException;

	Game getGame(String gameKeyStr) throws WPServiceException;

	List<Game> getMyTurnGames(User user) throws WPServiceException;

	List<InviteFriend> getTheirTurnsInviteFriend(User user);

	List<InviteGame> getTheirTurnsInviteGame(User user);

	InviteFriend getInviteFriend(String inviteFriendKeyStr)
			throws WPServiceException;

	void inviteFriends(User user, List<String> emails)
			throws WPServiceException;

	List<InviteFriend> getFriendInvitesByInviterKey(User user)
			throws WPServiceException;

	void updateFriendInvites(User user) throws WPServiceException;

	void updateFriendInvites();// background task

	void cancelFriendInvitation(User user, String email)
			throws WPServiceException;

	void ignoreFriendInvitation(String key) throws WPServiceException;

	InviteGame getInviteGame(String inviteGameKeyStr) throws WPServiceException;

	void ignoreGameInvitation(String key) throws WPServiceException;

	void createGame(final InviteGame inviteGame, final Answer answer)
			throws WPServiceException;

	void makeFriends(String friendInviteKeyStr) throws WPServiceException;

	List<User> getMyFriends(User u);

	void seedQuestions(User user) throws WPServiceException;

	Question saveQuestion(Question u) throws WPServiceException;

	Question getQuestion(String questionKey) throws WPServiceException;

	List<Question> getQuestionsPublic() throws WPServiceException;

	Answer saveAnswer(Answer u) throws WPServiceException;

	List<Answer> getAnswers(User u) throws WPServiceException;

	Answer getAnswer(String answerKey) throws WPServiceException;
}
