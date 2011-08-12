package com.wordpong.api.svc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.base.Predicate;
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.Game;
import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.Atomic;
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
import com.wordpong.api.svc.err.DaoException;
import com.wordpong.api.svc.err.WPServiceException;

public class SvcGameImpl implements SvcGame {
    private static final Logger log = Logger.getLogger(SvcGameImpl.class.getName());
    static final Comparator<User> FRIEND_ORDER = new Comparator<User>() {
        public int compare(User u1, User u2) {
            int u1pts = u1.getPoints();
            int u2pts = u2.getPoints();
            return u2pts - u1pts;
        }
    };

    public List<InviteFriend> getMyTurnInviteFriends(User user) throws WPServiceException {
        long start = System.currentTimeMillis();
        List<InviteFriend> result = new ArrayList<InviteFriend>();
        DaoInviteFriend dif = DaoInviteFriendFactory.getDaoInviteFriend();
        try {
            List<InviteFriend> invites = dif.getFriendInvitesByInviteeKey(user);
            for (InviteFriend invite : invites) {
                if (invite.isIgnored() == false) {
                    result.add(invite);
                }
            }
        } catch (DaoException e) {
            throw new WPServiceException("getMyTurnInviteFriends user:" + user, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    public List<Game> getMyTurnGames(User user) {
        long start = System.currentTimeMillis();
        List<Game> result = new ArrayList<Game>();
        DaoGame dig = DaoGameFactory.getDaoGame();
        try {
            List<Game> allGames = dig.getGamesByInviteeKey(user);
            // add games that are not completed
            for (Game g : allGames) {
                if (g.isCompleted() == false) {
                    result.add(g);
                }
            }

        } catch (DaoException e) {
            throw new WPServiceException("getMyTurnGames user:" + user, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    public List<InviteFriend> getTheirTurnsInviteFriend(User user) {
        long start = System.currentTimeMillis();
        List<InviteFriend> result = new ArrayList<InviteFriend>();
        DaoInviteFriend dfr = DaoInviteFriendFactory.getDaoInviteFriend();
        try {
            result = dfr.getFriendInvitesByInviterKey(user);
        } catch (DaoException e) {
            throw new WPServiceException("getTheirTurnsInviteFriend user:" + user, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    // @Override
    // public void setMyTurns(List<GameMyTurn> myTurns) {
    // List<InviteFriend> getFriendInvitesByKey(User user)
    // TODO call backend

    // }

    public void inviteFriends(User user, List<String> emails) throws WPServiceException {
        long start = System.currentTimeMillis();
        DaoInviteFriend f = DaoInviteFriendFactory.getDaoInviteFriend();
        try {
            f.inviteFriends(user, emails);
        } catch (DaoException e) {
            throw new WPServiceException("inviteFriends user: " + user + " emails:" + emails, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public List<InviteFriend> getFriendInvitesByInviterKey(User user) throws WPServiceException {
        long start = System.currentTimeMillis();
        List<InviteFriend> result = new ArrayList<InviteFriend>();
        DaoInviteFriend f = DaoInviteFriendFactory.getDaoInviteFriend();
        try {
            result = f.getFriendInvitesByInviterKey(user);
        } catch (DaoException e) {
            throw new WPServiceException("getFriendInvitesByInviterKey user: " + user, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    @Override
    public InviteFriend getInviteFriend(String inviteFriendKeyStr) throws WPServiceException {
        long start = System.currentTimeMillis();
        DaoInviteFriend f = DaoInviteFriendFactory.getDaoInviteFriend();
        InviteFriend result;
        try {
            result = f.getFriendInvite(inviteFriendKeyStr);
        } catch (DaoException e) {
            throw new WPServiceException("getInviteFriend inviteFriendKeyStr: " + inviteFriendKeyStr, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    public Game getGame(String gameKeyStr) throws WPServiceException {
        long start = System.currentTimeMillis();
        DaoGame f = DaoGameFactory.getDaoGame();
        Game result;
        try {
            result = f.getGame(gameKeyStr);
        } catch (DaoException e) {
            throw new WPServiceException("getGame gameKeyStr: " + gameKeyStr, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    public void cancelGameInvite(final Game g, final User u) throws WPServiceException {
        long start = System.currentTimeMillis();
        final DaoGame dg = DaoGameFactory.getDaoGame();
        final DaoUser du = DaoUserFactory.getDaoUser();
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
            throw new WPServiceException("cancelGameInvite game:" + g + " user:" + u, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public void cancelFriendInvitation(User user, String email) throws WPServiceException {
        long start = System.currentTimeMillis();
        DaoInviteFriend f = DaoInviteFriendFactory.getDaoInviteFriend();
        try {
            f.cancelInvitation(user, email);
        } catch (DaoException e) {
            throw new WPServiceException("cancelFriendInvitation user: " + user + " email:" + email, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public List<Question> getMyQuestions(User user) throws WPServiceException {
        long start = System.currentTimeMillis();
        List<Question> result;
        DaoQuestion dq = DaoQuestionFactory.getDaoQuestion();
        try {
            result = dq.getMyQuestions(user);
        } catch (DaoException e) {
            throw new WPServiceException("getMyQuestions user: " + user, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    @Override
    public Question createQuestion(Question q) throws WPServiceException {
        long start = System.currentTimeMillis();
        Question result = null;
        DaoQuestion dq = DaoQuestionFactory.getDaoQuestion();
        try {
            result = dq.createQuestion(q);
        } catch (DaoException e) {
            throw new WPServiceException("createQuestion question: " + q, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    @Override
    public void updateQuestion(Question q) throws WPServiceException {
        long start = System.currentTimeMillis();
        DaoQuestion dq = DaoQuestionFactory.getDaoQuestion();
        try {
            dq.updateQuestion(q);
        } catch (DaoException e) {
            throw new WPServiceException("updateQuestion question: " + q, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    @Override
    /*
     * * Scan the FriendInvitesTable for a user's email. For each InviteFriend
     * Try to find an existing user with that email If found, convert replace
     * the email with the key and update details
     */
    public void updateFriendInvites(User user) throws WPServiceException {
        long start = System.currentTimeMillis();

        final String msg = "updateFriendInvites: user:" + user;
        final User fUser = user;
        Predicate<Atomic> WORK = new Predicate<Atomic>() {
            public boolean apply(Atomic at) {
                boolean result = false;
                if (fUser != null) {
                    try {
                        // get FriendInvites for this user from db
                        DaoInviteFriend dfi = DaoInviteFriendFactory.getDaoInviteFriend();
                        List<InviteFriend> invites = dfi.getFriendInvitesByEmail(fUser);
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
            throw new WPServiceException("updateFriendInvites user:" + user, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    // cron calls this to move any missed invites to the users myTurn list
    public void updateFriendInvites() {
        long start = System.currentTimeMillis();
        DaoInviteFriend dfi = DaoInviteFriendFactory.getDaoInviteFriend();
        try {
            List<InviteFriend> invites = dfi.getAllFriendInvites();
            Map<String, Boolean> processed = new HashMap<String, Boolean>();
            if (invites != null) {
                DaoUser du = DaoUserFactory.getDaoUser();
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
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public void ignoreFriendInvitation(String friendInvitekeyStr) throws WPServiceException {
        long start = System.currentTimeMillis();
        DaoInviteFriend dfi = DaoInviteFriendFactory.getDaoInviteFriend();
        try {
            dfi.ignoreInvitation(friendInvitekeyStr);
        } catch (DaoException e) {
            throw new WPServiceException("ignoreFriendInvitation friendInvitekeyStr:" + friendInvitekeyStr, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    // Add each user to each others friend list
    // Create a new game for each user
    public void makeFriends(final String inviteFriendKeyStr) throws WPServiceException {
        long start = System.currentTimeMillis();
        final DaoInviteFriend dfi = DaoInviteFriendFactory.getDaoInviteFriend();
        final DaoUser du = DaoUserFactory.getDaoUser();
        InviteFriend fi = null;
        User inviteeUser = null;
        User inviterUser = null;
        try {
            fi = dfi.getFriendInvite(inviteFriendKeyStr);
            inviteeUser = du.getUser(fi.getInviteeKey());
            inviterUser = du.getUser(fi.getInviterKey());
        } catch (DaoException e1) {
            throw new WPServiceException("makeFriends1 inviteFriendKeyStr:" + inviteFriendKeyStr, e1);
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
            throw new WPServiceException("makeFriends inviteFriendKeyStr:" + inviteFriendKeyStr, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public List<User> getMyFriends(User u) {
        long start = System.currentTimeMillis();
        DaoUser du = DaoUserFactory.getDaoUser();
        List<User> friends = new ArrayList<User>();
        try {
            friends = du.getFriends(u);
            for (User f : friends) {
                int pts = u.getFriendPoints(f.getKeyString());
                f.setPoints(pts);
            }
            Collections.sort(friends, FRIEND_ORDER);

        } catch (Exception e) {
            throw new WPServiceException("getMyFriends user:" + u, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return friends;
    }

    /*
     * // Return a list of friends and the games they played // very expensive..
     * TODO: add caching // Query strategy starts with answers to derive friends
     * list
     * 
     * @Deprecated public List<User> getMyFriendsGames(User u) { DaoUser du =
     * DaoUserFactory.getDaoUser(); DaoGame dg = DaoGameFactory.getDaoGame();
     * DaoAnswer da = DaoAnswerFactory.getDaoAnswer(); List<User> result = new
     * ArrayList<User>(); try { // get all the answers for a user List<Answer>
     * as = da.getAnswers(u); // get all the games for a set of answers
     * List<Game> games = dg.getGamesByAnswers(as); // map users who played the
     * game to list of games Map<String, List<Game>> m = new HashMap<String,
     * List<Game>>(); for (Game g : games) { String uk =
     * g.getInviteeUserKeyString(); List<Game> gs = new ArrayList<Game>(); if
     * (m.containsKey(uk)) { gs = m.get(uk); } else { m.put(uk, gs); }
     * gs.add(g); } // get the list of keys to all my friends List<String>
     * keyStrings = new ArrayList<String>(m.keySet()); // read all the users who
     * are my friends result = du.getUsersByKeyStrings(keyStrings); // add the
     * list of games played to each user for (User user : result) { String k =
     * user.getKeyString(); // get the list of games from the map List<Game> gs
     * = m.get(k); user.setGames(gs); }
     * 
     * // add my friends who I haven't played any games with yet List<String>
     * fks = du.getFriendsKeyStrings(u); List<String> newFriendKeys = new
     * ArrayList<String>(); for (String ks : fks) { if (m.containsKey(ks) ==
     * false) { newFriendKeys.add(ks); } } if (newFriendKeys.size() > 0) {
     * List<User> newFriends = du.getUsersByKeyStrings(newFriendKeys);
     * result.addAll(newFriends); } Collections.sort(result, FRIEND_ORDER); }
     * catch (Exception e) { log.warning("getMyFriendsGames: user:" + u + " err"
     * + e.getMessage()); } return result; }
     */
    @Override
    public List<Game> getFriendGames(User user, User friend) throws WPServiceException {
        long start = System.currentTimeMillis();
        List<Game> result = new ArrayList<Game>();
        try {
            DaoGame dg = DaoGameFactory.getDaoGame();
            result = dg.getGames(user, friend);
        } catch (Exception e) {
            throw new WPServiceException("getFriendGames user:" + user + " friend:" + friend, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    public void seedQuestions(User user) throws WPServiceException {
        long start = System.currentTimeMillis();
        DaoTagQuestion f = DaoTagQuestionFactory.getDaoTagQuestion();
        try {
            f.seedQuestions(user);
        } catch (DaoException e) {
            throw new WPServiceException("seedQuestions user: " + user, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public Answer saveAnswer(Answer a) throws WPServiceException {
        long start = System.currentTimeMillis();
        Answer result = null;
        DaoAnswer f = DaoAnswerFactory.getDaoAnswer();
        try {
            result = f.save(a);
        } catch (DaoException e) {
            throw new WPServiceException("saveAnswer answer: " + a, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    @Override
    public List<Answer> getAnswers(User u) throws WPServiceException {
        long start = System.currentTimeMillis();
        List<Answer> result = null;
        DaoAnswer da = DaoAnswerFactory.getDaoAnswer();
        try {
            result = da.getAnswers(u);
        } catch (DaoException e) {
            throw new WPServiceException("getAnswers user: " + u, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    @Override
    public Answer getAnswer(String answerKeyStr) throws WPServiceException {
        long start = System.currentTimeMillis();
        DaoAnswer da = DaoAnswerFactory.getDaoAnswer();
        Answer result;
        try {
            result = da.getAnswer(answerKeyStr);
        } catch (DaoException e) {
            throw new WPServiceException("getAnswer answerKeyStr:" + answerKeyStr, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    @Override
    public Question getQuestion(String questionKeyStr) throws WPServiceException {
        long start = System.currentTimeMillis();
        DaoQuestion qa = DaoQuestionFactory.getDaoQuestion();
        Question result;
        try {
            result = qa.getQuestion(questionKeyStr);
        } catch (DaoException e) {
            throw new WPServiceException("getQuestion questionKeyStr:" + questionKeyStr, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    public void createGame(final Game g, final User u) throws WPServiceException {
        long start = System.currentTimeMillis();
        final DaoGame dg = DaoGameFactory.getDaoGame();
        final DaoUser du = DaoUserFactory.getDaoUser();
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
            throw new WPServiceException("createGame game:" + g + " user:" + u, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public void finishGame(final String gameKeyString) throws WPServiceException {
        long start = System.currentTimeMillis();
        final DaoGame dg = DaoGameFactory.getDaoGame();
        final DaoUser du = DaoUserFactory.getDaoUser();
        final String msg = "finishGame key:" + gameKeyString;
        Predicate<Atomic> WORK = new Predicate<Atomic>() {
            public boolean apply(Atomic at) {
                boolean result = false;
                try {
                    Game g = dg.getGame(at, gameKeyString);
                    g.setCompleted(true);
                    dg.saveGame(at, g);

                    String inviterKey = g.getInviterUserKeyString();
                    String inviteeKey = g.getInviteeUserKeyString();
                    User inviter = du.getUser(at, inviterKey);
                    inviter.addFriendPoints(inviteeKey, g.getPoints());
                    inviter.removeGame(g);
                    du.save(at, inviter);

                    User invitee = du.getUser(at, inviteeKey);
                    invitee.addFriendPoints(inviterKey, g.getPoints());
                    du.save(at, invitee);
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
            throw new WPServiceException("finishGame gameKeyString:" + gameKeyString, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public List<Question> getUnansweredQuestions(User user) throws WPServiceException {
        long start = System.currentTimeMillis();
        List<Question> result = new ArrayList<Question>();
        DaoQuestion dq = DaoQuestionFactory.getDaoQuestion();
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
            throw new WPServiceException("getUnansweredQuestions user: " + user, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    @Override
    public User getUser(String keyString) throws WPServiceException {
        long start = System.currentTimeMillis();
        DaoUser du = DaoUserFactory.getDaoUser();
        User result;
        try {
            result = du.getUser(keyString);
        } catch (DaoException e) {
            throw new WPServiceException("getUser keyString:" + keyString, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;

    }

    // Refresh user from database
    @Override
    public User getUser(User u) throws WPServiceException {
        long start = System.currentTimeMillis();
        DaoUser du = DaoUserFactory.getDaoUser();
        User result;
        try {
            result = du.getUser(u);
        } catch (DaoException e) {
            throw new WPServiceException("getUser user:" + u, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    @Override
    public List<Game> getTheirTurnsGame(User user) throws WPServiceException {
        long start = System.currentTimeMillis();
        List<Game> result = new ArrayList<Game>();
        DaoGame dg = DaoGameFactory.getDaoGame();
        try {
            result = dg.getTheirTurnGames(user);
        } catch (DaoException e) {
            // return empty games list
            // TODO: always reload user from db upon error
            // in case user made changes in another session
            throw new WPServiceException("getTheirTurnsGame user:" + user, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

}
