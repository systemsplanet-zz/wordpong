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
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.DaoAnswer;
import com.wordpong.api.svc.dao.DaoAnswerFactory;
import com.wordpong.api.svc.dao.DaoGame;
import com.wordpong.api.svc.dao.DaoGameFactory;
import com.wordpong.api.svc.dao.DaoInviteFriend;
import com.wordpong.api.svc.dao.DaoInviteFriendFactory;
import com.wordpong.api.svc.dao.DaoQuestion;
import com.wordpong.api.svc.dao.DaoQuestionFactory;
import com.wordpong.api.svc.dao.DaoTagQuestion;
import com.wordpong.api.svc.dao.DaoTagQuestionFactory;
import com.wordpong.api.svc.dao.DaoUser;
import com.wordpong.api.svc.dao.DaoUserFactory;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public class SvcGameImpl implements SvcGame {
    private static final Logger log = Logger.getLogger(SvcGameImpl.class.getName());
    static final Comparator<User> FRIEND_ORDER = new Comparator<User>() {
        public int compare(User u1, User u2) {
            return u2.getTotalPoints() - u1.getTotalPoints();
        }
    };

    public List<InviteFriend> getMyTurnInviteFriends(User user) throws WPServiceException {
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

    // @Override
    // public void setMyTurns(List<GameMyTurn> myTurns) {
    // List<InviteFriend> getFriendInvitesByKey(User user)
    // TODO call backend

    // }

    public void inviteFriends(User user, List<String> emails) throws WPServiceException {
        DaoInviteFriend f = DaoInviteFriendFactory.getFriendInviteDao();
        try {
            f.inviteFriends(user, emails);
        } catch (DaoException e) {
            throw new WPServiceException("inviteFriends err: " + e.getMessage());
        }
    }

    @Override
    public List<InviteFriend> getFriendInvitesByInviterKey(User user) throws WPServiceException {
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
    public InviteFriend getInviteFriend(String inviteFriendKeyStr) throws WPServiceException {
        DaoInviteFriend f = DaoInviteFriendFactory.getFriendInviteDao();
        InviteFriend result;
        try {
            result = f.getFriendInvite(inviteFriendKeyStr);
        } catch (DaoException e) {
            throw new WPServiceException("getInviteFriend err: " + e.getMessage());
        }
        return result;
    }

    public Game getGame(String gameKeyStr) throws WPServiceException {
        DaoGame f = DaoGameFactory.getGameDao();
        Game result;
        try {
            result = f.getGame(gameKeyStr);
        } catch (DaoException e) {
            throw new WPServiceException("getGame err: " + e.getMessage());
        }
        return result;
    }

    public void cancelGameInvite(final Game g, final User u) throws WPServiceException {
        final DaoGame dg = DaoGameFactory.getGameDao();
        final DaoUser du = DaoUserFactory.getUserDao();
        final String msg = "cancelGameInvite Game:" + g + " User:" + u;
        Predicate<Atomic> WORK = new Predicate<Atomic>() {
            public boolean apply(Atomic at) {
                boolean result = false;
                try {
                    u.removeGame(g);
                    du.save(at, u);
                    dg.cancelGameInvite(at, g);
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

    @Override
    public void cancelFriendInvitation(User user, String email) throws WPServiceException {
        DaoInviteFriend f = DaoInviteFriendFactory.getFriendInviteDao();
        try {
            f.cancelInvitation(user, email);
        } catch (DaoException e) {
            throw new WPServiceException("cancelInvitation err: " + e.getMessage());
        }
    }

    @Override
    public List<Question> getMyQuestions(User user) throws WPServiceException {
        List<Question> result;
        DaoQuestion dq = DaoQuestionFactory.getQuestionDao();
        try {
            result = dq.getQuestions(user);
        } catch (DaoException e) {
            throw new WPServiceException("getMyQuestions err: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Question createQuestion(Question q) throws WPServiceException {
        Question result = null;
        DaoQuestion dq = DaoQuestionFactory.getQuestionDao();
        try {
            result = dq.createQuestion(q);
        } catch (DaoException e) {
            throw new WPServiceException("createQuestion err: " + e.getMessage());
        }
        return result;
    }

    @Override
    public void updateQuestion(Question q) throws WPServiceException {
        DaoQuestion dq = DaoQuestionFactory.getQuestionDao();
        try {
            dq.updateQuestion(q);
        } catch (DaoException e) {
            throw new WPServiceException("updateQuestion err: " + e.getMessage());
        }
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
                        DaoInviteFriend dfi = DaoInviteFriendFactory.getFriendInviteDao();
                        List<InviteFriend> invites = dfi.getFriendInvitesByEmail(fUser);
                        final String msg = "updateFriendInvites: user:" + fUser + " invites:" + invites;
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
    public void ignoreFriendInvitation(String friendInvitekeyStr) throws WPServiceException {
        DaoInviteFriend dfi = DaoInviteFriendFactory.getFriendInviteDao();
        try {
            dfi.ignoreInvitation(friendInvitekeyStr);
        } catch (DaoException e) {
            throw new WPServiceException(e.getMessage());
        }
    }

    // Add each user to each others friend list
    // Create a new game for each user
    public void makeFriends(final String inviteFriendKeyStr) throws WPServiceException {
        final DaoInviteFriend dfi = DaoInviteFriendFactory.getFriendInviteDao();
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
        final String msg = "makeFriends friendInviteKeyStr" + inviteFriendKeyStr + " fi:" + fi + " inviteeUser:"
                + inviteeUser + " inviterUser" + inviterUser;
        final InviteFriend ffi = fi;
        final User fInviteeUser = inviteeUser;
        final User fInviterUser = inviterUser;
        Predicate<Atomic> WORK = new Predicate<Atomic>() {
            public boolean apply(Atomic at) {
                boolean result = false;
                try {
                    if (fInviteeUser != null && fInviterUser != null && ffi != null) {
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
                String uk = g.getInviteeUserKeyString();
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
            log.warning("getMyFriendsGames: user:" + u + " err" + e.getMessage());
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
            throw new WPServiceException("getAnswer key:" + answerKeyStr + " err: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Question getQuestion(String questionKeyStr) throws WPServiceException {
        DaoQuestion qa = DaoQuestionFactory.getQuestionDao();
        Question result;
        try {
            result = qa.getQuestion(questionKeyStr);
        } catch (DaoException e) {
            throw new WPServiceException("getQuestion key:" + questionKeyStr + " err: " + e.getMessage());
        }
        return result;
    }

    public void createGame(final Game g, final User u) throws WPServiceException {
        final DaoGame dg = DaoGameFactory.getGameDao();
        final DaoUser du = DaoUserFactory.getUserDao();
        final String msg = "createGame Game:" + g + " User:" + u;
        Predicate<Atomic> WORK = new Predicate<Atomic>() {
            public boolean apply(Atomic at) {
                boolean result = false;
                try {
                    // get the latest user
                    User user = du.getUser(at, u);
                    // create a new game
                    Game game = dg.save(at, g);
                    // add the game to the list of active games
                    user.addGame(game);
                    du.save(at, user);
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

    @Override
    public void finishGame(final String gameKeyString) throws WPServiceException {
        final DaoGame dg = DaoGameFactory.getGameDao();
        final DaoUser du = DaoUserFactory.getUserDao();
        final String msg = "finishGame key:" + gameKeyString;
        Predicate<Atomic> WORK = new Predicate<Atomic>() {
            public boolean apply(Atomic at) {
                boolean result = false;
                try {
                    Game g = dg.getGame(at, gameKeyString);
                    g.setCompleted(true);
                    dg.saveGame(at, g);
                    String uk = g.getInviterUserKeyString();
                    User u = du.getUser(at, uk);
                    u.removeGame(g);
                    du.save(at, u);
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

    @Override
    public List<Question> getUnansweredQuestions(User user) throws WPServiceException {
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
            List<Question> allPublic = dq.getQuestions(user);
            if (allPublic != null) {
                for (Question q : allPublic) {
                    String ks = q.getKeyString();
                    if (ks != null && m.containsKey(ks) == false) {
                        result.add(q);
                    }
                }
            }
        } catch (DaoException e) {
            throw new WPServiceException("getQuestionsPublic err: " + e.getMessage());
        }
        return result;
    }

    // Refresh user from database
    @Override
    public User getUser(User u) throws WPServiceException {
        DaoUser du = DaoUserFactory.getUserDao();
        User result;
        try {
            result = du.getUser(u);
        } catch (DaoException e) {
            throw new WPServiceException("getUser user:" + u + " err: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Game> getTheirTurnsGame(User user) throws WPServiceException {
        List<Game> result = new ArrayList<Game>();
        DaoGame dg = DaoGameFactory.getGameDao();
        try {
            result = dg.getTheirTurnGames(user);
        } catch (DaoException e) {
            throw new WPServiceException("getTheirTurnsGame user:" + user + " err: " + e.getMessage());
        }
        return result;
    }

}
