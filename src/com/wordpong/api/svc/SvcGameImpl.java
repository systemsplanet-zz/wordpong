package com.wordpong.api.svc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.base.Predicate;
import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.Game;
import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.model.InviteGame;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.DaoAnswer;
import com.wordpong.api.svc.dao.DaoAnswerFactory;
import com.wordpong.api.svc.dao.DaoGame;
import com.wordpong.api.svc.dao.DaoGameFactory;
import com.wordpong.api.svc.dao.DaoInviteFriend;
import com.wordpong.api.svc.dao.DaoInviteFriendFactory;
import com.wordpong.api.svc.dao.DaoInviteGame;
import com.wordpong.api.svc.dao.DaoInviteGameFactory;
import com.wordpong.api.svc.dao.DaoQuestion;
import com.wordpong.api.svc.dao.DaoQuestionFactory;
import com.wordpong.api.svc.dao.DaoTagQuestion;
import com.wordpong.api.svc.dao.DaoTagQuestionFactory;
import com.wordpong.api.svc.dao.DaoUser;
import com.wordpong.api.svc.dao.DaoUserFactory;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public class SvcGameImpl implements SvcGame {
	private static final Logger log = Logger.getLogger(SvcGameImpl.class
			.getName());
	static final Comparator<User> FRIEND_ORDER = new Comparator<User>() {
		public int compare(User u1, User u2) {
			return u2.getTotalPoints() - u1.getTotalPoints();
		}
	};

	public List<InviteFriend> getMyTurnInviteFriends(User user)
			throws WPServiceException {
		List<InviteFriend> result = new ArrayList<InviteFriend>();
		DaoInviteFriend dif = DaoInviteFriendFactory.getFriendInviteDao();
		try {
			List<InviteFriend> invites = dif.getFriendInvitesByInviteeKey(user);
			for (InviteFriend invite : invites) {
				if (invite.isIgnored() == false) {
					result.add(invite);
				}
			}
		} catch (DaoException e) {
			log.warning("getMyTurnInviteFriends err: " + e.getMessage());
			throw new WPServiceException(e.getMessage());
		}
		return result;
	}

	public List<InviteGame> getMyTurnInviteGames(User user)
			throws WPServiceException {
		List<InviteGame> result = new ArrayList<InviteGame>();
		DaoInviteGame dif = DaoInviteGameFactory.getInviteGameDao();
		try {
			List<InviteGame> invites = dif
					.getGameActivePlayerByInviteeKey(user);
			for (InviteGame invite : invites) {
				if (invite.isIgnored() == false) {
					result.add(invite);
				}
			}
		} catch (DaoException e) {
			log.warning("getMyTurnInviteGames err: " + e.getMessage());
			throw new WPServiceException(e.getMessage());
		}
		return result;
	}

	public List<Game> getMyTurnGames(User user) {
		List<Game> result = new ArrayList<Game>();
		DaoGame dig = DaoGameFactory.getGameDao();
		try {
			List<Game> allGames = dig.getGamesByInviteeKey(user);
			// add games that are not completed
			for (Game g : allGames) {
				if (g.isCompleted() == false) {
					result.add(g);
				}
			}

		} catch (DaoException e) {
			log.fine("getTheirTurnsGames err:" + e.getMessage());
		}

		return result;
	}

	public List<InviteFriend> getTheirTurnsInviteFriend(User user) {
		List<InviteFriend> result = new ArrayList<InviteFriend>();
		DaoInviteFriend dfr = DaoInviteFriendFactory.getFriendInviteDao();
		try {
			result = dfr.getFriendInvitesByInviterKey(user);
		} catch (DaoException e) {
			log.fine("getTheirTurnsInviteFriend err:" + e.getMessage());
		}
		return result;
	}

	public List<InviteGame> getTheirTurnsInviteGame(User user) {
		List<InviteGame> result = new ArrayList<InviteGame>();
		DaoInviteGame dig = DaoInviteGameFactory.getInviteGameDao();
		try {
			result = dig.getGameInvitesByInviterKey(user);
		} catch (DaoException e) {
			log.fine("getTheirTurnsInviteGame err:" + e.getMessage());
		}

		return result;
	}

	// @Override
	// public void setMyTurns(List<GameMyTurn> myTurns) {
	// List<InviteFriend> getFriendInvitesByKey(User user)
	// TODO call backend

	// }

	public void inviteFriends(User user, List<String> emails)
			throws WPServiceException {
		DaoInviteFriend f = DaoInviteFriendFactory.getFriendInviteDao();
		try {
			f.inviteFriends(user, emails);
		} catch (DaoException e) {
			throw new WPServiceException("inviteFriends err: " + e.getMessage());
		}
	}

	@Override
	public List<InviteFriend> getFriendInvitesByInviterKey(User user)
			throws WPServiceException {
		List<InviteFriend> result = new ArrayList<InviteFriend>();
		DaoInviteFriend f = DaoInviteFriendFactory.getFriendInviteDao();
		try {
			result = f.getFriendInvitesByInviterKey(user);
		} catch (DaoException e) {
			throw new WPServiceException("inviteFriends err: " + e.getMessage());
		}
		return result;
	}

	@Override
	public InviteFriend getInviteFriend(String inviteFriendKeyStr)
			throws WPServiceException {
		DaoInviteFriend f = DaoInviteFriendFactory.getFriendInviteDao();
		InviteFriend result;
		try {
			result = f.getFriendInvite(inviteFriendKeyStr);
		} catch (DaoException e) {
			throw new WPServiceException("getInviteFriend err: "
					+ e.getMessage());
		}
		return result;
	}

	public InviteGame getInviteGame(String inviteGameKeyStr)
			throws WPServiceException {
		DaoInviteGame f = DaoInviteGameFactory.getInviteGameDao();
		InviteGame result;
		try {
			result = f.getInviteGame(inviteGameKeyStr);
		} catch (DaoException e) {
			throw new WPServiceException("getinviteGame err: " + e.getMessage());
		}
		return result;
	}

	public Game getGame(String inviteGameKeyStr) throws WPServiceException {
		DaoGame f = DaoGameFactory.getGameDao();
		Game result;
		try {
			result = f.getGame(inviteGameKeyStr);
		} catch (DaoException e) {
			throw new WPServiceException("getinviteGame err: " + e.getMessage());
		}
		return result;
	}

	@Override
	public void cancelFriendInvitation(User user, String email)
			throws WPServiceException {
		DaoInviteFriend f = DaoInviteFriendFactory.getFriendInviteDao();
		try {
			f.cancelInvitation(user, email);
		} catch (DaoException e) {
			throw new WPServiceException("cancelInvitation err: "
					+ e.getMessage());
		}
	}

	@Override
	public Question saveQuestion(Question q) throws WPServiceException {
		Question result = null;
		DaoQuestion f = DaoQuestionFactory.getQuestionDao();
		try {
			result = f.save(q);
		} catch (DaoException e) {
			throw new WPServiceException("saveQuestion err: " + e.getMessage());
		}
		return result;
	}

	@Override
	/*
	 * * Scan the FriendInvitesTable for a user's email. For each InviteFriend
	 * Try to find an existing user with that email If found, convert replace
	 * the email with the key and update details
	 */
	public void updateFriendInvites(User user) throws WPServiceException {
		if (user != null) {
			final String msg = "updateFriendInvites: user:" + user;
			final User fUser = user;
			Predicate<Atomic> WORK = new Predicate<Atomic>() {
				public boolean apply(Atomic at) {
					boolean result = false;
					try {
						// get FriendInvites for this user from db
						DaoInviteFriend dfi = DaoInviteFriendFactory
								.getFriendInviteDao();
						List<InviteFriend> invites = dfi
								.getFriendInvitesByEmail(fUser);
						final String msg = "updateFriendInvites: user:" + fUser
								+ " invites:" + invites;
						log.info(msg);
						if (invites != null && invites.size() > 0) {
							for (InviteFriend invite : invites) {
								invite.setInvitee(fUser);
							}
							// write new friend requests to database
							at.put(invites);
						}
						result = true;
					} catch (DaoException e) {
					}
					return result;
				}

				public String toString() {
					return msg;
				}
			};
			try {
				Atomic.transact(WORK);
			} catch (Exception e) {
				throw new WPServiceException(e.getMessage());
			}
		}
	}

	// cron calls this to move any missed invites to the users myTurn list
	public void updateFriendInvites() {
		DaoInviteFriend dfi = DaoInviteFriendFactory.getFriendInviteDao();
		try {
			List<InviteFriend> invites = dfi.getAllFriendInvites();
			Map<String, Boolean> processed = new HashMap<String, Boolean>();
			if (invites != null) {
				DaoUser du = DaoUserFactory.getUserDao();
				for (InviteFriend fi : invites) {
					try {
						String email = fi.getInviteeDetails();
						if (processed.containsKey(email) == false) {
							processed.put(email, true);
							User user = du.findByEmail(email);
							updateFriendInvites(user);
						}
					} catch (DaoException e1) {
						// ignore user not found errors
					}
				}
			}
		} catch (Exception e) {
			log.warning(e.getMessage());
		}
	}

	@Override
	public void ignoreFriendInvitation(String friendInvitekeyStr)
			throws WPServiceException {
		DaoInviteFriend dfi = DaoInviteFriendFactory.getFriendInviteDao();
		try {
			dfi.ignoreInvitation(friendInvitekeyStr);
		} catch (DaoException e) {
			throw new WPServiceException(e.getMessage());
		}
	}

	@Override
	public void ignoreGameInvitation(String friendInvitekeyStr)
			throws WPServiceException {
		DaoInviteGame dig = DaoInviteGameFactory.getInviteGameDao();
		try {
			dig.ignoreInvitation(friendInvitekeyStr);
		} catch (DaoException e) {
			throw new WPServiceException(e.getMessage());
		}
	}

	// Add each user to each others friend list
	// Create a new game for each user
	public void makeFriends(final String inviteFriendKeyStr)
			throws WPServiceException {
		final DaoInviteFriend dfi = DaoInviteFriendFactory.getFriendInviteDao();
		final DaoInviteGame dig = DaoInviteGameFactory.getInviteGameDao();
		final DaoUser du = DaoUserFactory.getUserDao();
		InviteFriend fi = null;
		User inviteeUser = null;
		User inviterUser = null;
		try {
			fi = dfi.getFriendInvite(inviteFriendKeyStr);
			inviteeUser = du.getUser(fi.getInviteeKey());
			inviterUser = du.getUser(fi.getInviterKey());
		} catch (DaoException e1) {
			throw new WPServiceException(e1.getMessage());
		}
		final String msg = "makeFriends friendInviteKeyStr"
				+ inviteFriendKeyStr + " fi:" + fi + " inviteeUser:"
				+ inviteeUser + " inviterUser" + inviterUser;
		final InviteFriend ffi = fi;
		final User fInviteeUser = inviteeUser;
		final User fInviterUser = inviterUser;
		Predicate<Atomic> WORK = new Predicate<Atomic>() {
			public boolean apply(Atomic at) {
				boolean result = false;
				try {
					if (fInviteeUser != null && fInviterUser != null
							&& ffi != null) {
						dig.createGameInvites(at, fInviteeUser, fInviterUser);
						du.makeFriends(at, fInviteeUser, fInviterUser);
						dfi.removeInvitation(at, ffi);
						result = true;
					}
				} catch (DaoException e) {
				}
				return result;
			}

			public String toString() {
				return msg;
			}
		};
		try {
			Atomic.transact(WORK);
		} catch (Exception e) {
			throw new WPServiceException(e.getMessage());
		}
	}

	@Override
	public List<User> getMyFriends(User u) {
		DaoUser du = DaoUserFactory.getUserDao();
		List<User> friends = new ArrayList<User>();
		try {
			friends = du.getFriends(u);
		} catch (Exception e) {
			log.warning("getMyFriends: err" + e.getMessage());
		}
		return friends;
	}

	// Return a list of friends and the games they played
	// very expensive.. TODO: add caching
	// Query strategy starts with answers to derive friends list
	@Override
	public List<User> getMyFriendsGames(User u) {
		DaoUser du = DaoUserFactory.getUserDao();
		DaoGame dg = DaoGameFactory.getGameDao();
		DaoAnswer da = DaoAnswerFactory.getAnswerDao();
		List<User> result = new ArrayList<User>();
		try {
			// get all the answers for a user
			List<Answer> as = da.getAnswers(u);
			// get all the games for a set of answers
			List<Game> games = dg.getGamesByAnswers(as);
			// map users who played the game to list of games
			Map<String, List<Game>> m = new HashMap<String, List<Game>>();
			for (Game g : games) {
				String uk = g.getUserKeyString();
				List<Game> gs = new ArrayList<Game>();
				if (m.containsKey(uk)) {
					gs = m.get(uk);
				} else {
					m.put(uk, gs);
				}
				gs.add(g);
			}
			// get the list of keys to all my friends
			List<String> keyStrings = new ArrayList<String>(m.keySet());
			// read all the users who are my friends
			result = du.getUsersByKeyStrings(keyStrings);
			// add the list of games played to each user
			for (User user : result) {
				String k = user.getKeyString();
				// get the list of games from the map
				List<Game> gs = m.get(k);
				user.setGames(gs);
			}

			// add my friends who I haven't played any games with yet
			List<String> fks = du.getFriendsKeyStrings(u);
			List<String> newFriendKeys = new ArrayList<String>();
			for (String ks : fks) {
				if (m.containsKey(ks) == false) {
					newFriendKeys.add(ks);
				}
			}
			if (newFriendKeys.size() > 0) {
				List<User> newFriends = du.getUsersByKeyStrings(newFriendKeys);
				result.addAll(newFriends);
			}
			Collections.sort(result, FRIEND_ORDER);
		} catch (Exception e) {
			log.warning("getMyFriendsGames: user:" + u + " err"
					+ e.getMessage());
		}
		return result;
	}

	public void seedQuestions(User user) throws WPServiceException {
		DaoTagQuestion f = DaoTagQuestionFactory.getTagQuestionDao();
		try {
			f.seedQuestions(user);
		} catch (DaoException e) {
			throw new WPServiceException("seedQuestions err: " + e.getMessage());
		}
	}

	@Override
	public Answer saveAnswer(Answer a) throws WPServiceException {
		Answer result = null;
		DaoAnswer f = DaoAnswerFactory.getAnswerDao();
		try {
			result = f.save(a);
		} catch (DaoException e) {
			throw new WPServiceException("saveAnswer err: " + e.getMessage());
		}
		return result;
	}

	@Override
	public List<Answer> getAnswers(User u) throws WPServiceException {
		List<Answer> result = null;
		DaoAnswer da = DaoAnswerFactory.getAnswerDao();
		try {
			result = da.getAnswers(u);
		} catch (DaoException e) {
			throw new WPServiceException("getAnswers err: " + e.getMessage());
		}
		return result;
	}

	@Override
	public Answer getAnswer(String answerKeyStr) throws WPServiceException {
		DaoAnswer da = DaoAnswerFactory.getAnswerDao();
		Answer result;
		try {
			result = da.getAnswer(answerKeyStr);
		} catch (DaoException e) {
			throw new WPServiceException("getAnswer key:" + answerKeyStr
					+ " err: " + e.getMessage());
		}
		return result;
	}

	@Override
	public Question getQuestion(String questionKeyStr)
			throws WPServiceException {
		DaoQuestion qa = DaoQuestionFactory.getQuestionDao();
		Question result;
		try {
			result = qa.getQuestion(questionKeyStr);
		} catch (DaoException e) {
			throw new WPServiceException("getQuestion key:" + questionKeyStr
					+ " err: " + e.getMessage());
		}
		return result;
	}

	public void createGame(Game g) throws WPServiceException {
		DaoGame dg = DaoGameFactory.getGameDao();
		try {
			dg.save(g);
		} catch (DaoException e) {
			throw new WPServiceException(e.getMessage());
		}
	}

	/**
	 * Create a new game and remove the game invite
	 */
	@Override
	public void createGame(final InviteGame inviteGame, final Answer answer)
			throws WPServiceException {
		Predicate<Atomic> WORK = new Predicate<Atomic>() {
			String msg = "createGame: " + inviteGame + " answer:" + answer;
			final String answerKeyString = answer.getKeyString();
			final String questionDescription = answer.getQuestionDescription();
			final String inviterDetails = inviteGame.getInviteeDetails();

			public boolean apply(Atomic at) {
				boolean result = false;
				try {
					if (answerKeyString != null && inviteGame != null
							&& inviteGame.getKey() != null) {
						DaoGame dg = DaoGameFactory.getGameDao();
						Game g = new Game();
						g.setInviterDetails(inviterDetails);
						g.setQuestionDescription(questionDescription);
						g.setAnswersKeyString(answerKeyString);
						g.setInvitee(inviteGame);
						dg.save(at, g);
						DaoInviteGame dig = DaoInviteGameFactory
								.getInviteGameDao();
						dig.removeInviteGame(at, inviteGame);
						result = true;
					}
				} catch (DaoException e) {
				}
				return result;
			}

			public String toString() {
				return msg;
			}
		};
		try {
			Atomic.transact(WORK);
		} catch (Exception e) {
			throw new WPServiceException(e.getMessage());
		}
	}

	@Override
	public List<Question> getUnansweredQuestions(User user)
			throws WPServiceException {
		List<Question> result = new ArrayList<Question>();
		DaoQuestion dq = DaoQuestionFactory.getQuestionDao();
		try {
			Map<String, Boolean> m = new HashMap<String, Boolean>();
			// get the list of answers for this user in a map
			List<Answer> as = getAnswers(user);
			if (as != null) {
				for (Answer a : as) {
					String ks = a.getQuestionKeyString();
					if (ks != null) {
						m.put(ks, true);
					}
				}
			}
			// get the list of all public questions
			// add ones that the user has not already answered
			List<Question> allPublic = dq.getPublic();
			if (allPublic != null) {
				for (Question q : allPublic) {
					String ks = q.getKeyString();
					if (ks != null && m.containsKey(ks) == false) {
						result.add(q);
					}
				}
			}
		} catch (DaoException e) {
			throw new WPServiceException("getQuestionsPublic err: "
					+ e.getMessage());
		}
		return result;
	}

	@Override
	public void finishGame(String gameKeyString) throws WPServiceException {
		DaoGame dg = DaoGameFactory.getGameDao();
		try {
			dg.finishGame(gameKeyString);
		} catch (DaoException e) {
			throw new WPServiceException("finishGame key:" + gameKeyString
					+ " err: " + e.getMessage());
		}
	}

	// Refresh user from database
	@Override
	public User getUser(User u) throws WPServiceException {
		DaoUser du = DaoUserFactory.getUserDao();
		User result;
		try {
			result = du.getUser(u);
		} catch (DaoException e) {
			throw new WPServiceException("getUser user:" + u
					+ " err: " + e.getMessage());		}
		return result;
	}
}
