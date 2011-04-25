package com.wordpong.api.svc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Key;
import com.google.common.base.Predicate;
import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.model.InviteGame;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.FriendGames;
import com.wordpong.api.pojo.GameMyTurn;
import com.wordpong.api.pojo.GameTheirTurn;
import com.wordpong.api.svc.dao.DaoInviteFriend;
import com.wordpong.api.svc.dao.DaoInviteFriendFactory;
import com.wordpong.api.svc.dao.DaoInviteGame;
import com.wordpong.api.svc.dao.DaoInviteGameFactory;
import com.wordpong.api.svc.dao.DaoQuestion;
import com.wordpong.api.svc.dao.DaoQuestionFactory;
import com.wordpong.api.svc.dao.DaoUser;
import com.wordpong.api.svc.dao.DaoUserFactory;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;
import com.wordpong.api.svc.util.SvcLocale;

public class SvcGameImpl implements SvcGame {
    private static final Logger log = Logger.getLogger(SvcGameImpl.class.getName());

    @Override
    public List<GameMyTurn> getMyTurns(User user) {
        List<GameMyTurn> result = new ArrayList<GameMyTurn>();
        DaoInviteFriend dif = DaoInviteFriendFactory.getFriendInviteDao();

        try {
            List<InviteFriend> requests = dif.getFriendInvitesByInviteeKey(user);
            for (InviteFriend fr : requests) {
                if (fr.isIgnored() == false) {
                    GameMyTurn gmt = new GameMyTurn();
                    gmt.setAction(GameMyTurn.Action.InvitationRequest);
                    gmt.setId(fr.getInviterEmail());
                    String newFriend = SvcLocale.get("newFriend");
                    gmt.setDetails(newFriend + ": " + fr.getInviterDetails());
                    gmt.setCreatedAtString(fr.getCreatedAtString());
                    gmt.setKey(fr.getKeyString());
                    result.add(gmt);
                }
            }

            // get the games where it's my turn
            DaoInviteGame dig = DaoInviteGameFactory.getInviteGameDao();
            List<InviteGame> games = dig.getGameActivePlayerByInviteeKey(user);
            for (InviteGame g : games) {
                if (g.isIgnored() == false) {
                    GameMyTurn gmt = new GameMyTurn();
                    gmt.setAction(GameMyTurn.Action.CreateGame);
                    gmt.setId(g.getInviterEmail());
                    String newGame = SvcLocale.get("newGame");
                    gmt.setDetails(newGame + ": " + g.getInviterDetails());
                    gmt.setCreatedAtString(g.getCreatedAtString());
                    gmt.setKey(g.getKeyString());
                    result.add(gmt);
                }
            }
        } catch (DaoException e) {
            log.warning("getMyTurns err: " + e.getMessage());
        }
        // TODO: gmt.setAction(Action.InviteAccepted);
        return result;
    }

    public List<GameTheirTurn> getTheirTurns(User user) {
        List<GameTheirTurn> turns = new ArrayList<GameTheirTurn>();
        DaoInviteFriend dfr = DaoInviteFriendFactory.getFriendInviteDao();
        try {
            List<InviteFriend> invites = dfr.getFriendInvitesByInviterKey(user);
            if (invites != null) {
                for (InviteFriend fi : invites) {
                    GameTheirTurn gtt = new GameTheirTurn();
                    gtt.setId(fi.getInviteeEmail());
                    String newFriend = SvcLocale.get("newFriend");
                    gtt.setDetails(newFriend + ": " + fi.getInviteeDetails());
                    gtt.setAction(GameTheirTurn.Action.InvitationSent);
                    gtt.setCreatedAtString(fi.getCreatedAtString());
                    turns.add(gtt);
                }
            }
        } catch (DaoException e) {
            log.fine("err:" + e.getMessage());
        }
        return turns;
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
    public void cancelInvitation(User user, String email) throws WPServiceException {
        DaoInviteFriend f = DaoInviteFriendFactory.getFriendInviteDao();
        try {
            f.cancelInvitation(user, email);
        } catch (DaoException e) {
            throw new WPServiceException("cancelInvitation err: " + e.getMessage());
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
                        DaoInviteFriend dfi = DaoInviteFriendFactory.getFriendInviteDao();
                        List<InviteFriend> invites = dfi.getFriendInvitesByEmail(fUser);
                        final String msg = "updateFriendInvites: user:" + fUser + " invites:" + invites;
                        log.info(msg);
                        Key key = fUser.getKey();
                        String details = fUser.getDetails();
                        if (invites != null && invites.size() > 0) {
                            for (InviteFriend invite : invites) {
                                invite.setInviteeKey(key);
                                invite.setInviteeDetails(details);
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
    public void ignoreInvitation(String friendInvitekeyStr) throws WPServiceException {
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
        final String msg = "makeFriends friendInviteKeyStr" + inviteFriendKeyStr + " fi:" + fi + " inviteeUser:" + inviteeUser + " inviterUser" + inviterUser;
        final InviteFriend ffi = fi;
        final User fInviteeUser = inviteeUser;
        final User fInviterUser = inviterUser;
        Predicate<Atomic> WORK = new Predicate<Atomic>() {
            public boolean apply(Atomic at) {
                boolean result = false;
                try {
                    if (fInviteeUser != null && fInviterUser != null && ffi != null) {
                        dig.createGames(at, fInviteeUser, fInviterUser);
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
    public List<FriendGames> getMyFriendGames(User u) {
        DaoUser du = DaoUserFactory.getUserDao();
        Set<Key> keySet = u.getFriends();
        List<Key> keyList = new ArrayList<Key>(keySet);
        List<FriendGames> fgs = new ArrayList<FriendGames>();
        try {
            List<User> us = du.getUsers(keyList);
            for (User usr : us) {
                FriendGames fg = new FriendGames();
                fg.setFriend(usr);
                fgs.add(fg);
                // TODO: get games
            }
        } catch (Exception e) {
            log.warning("getMyFriendGames: err" + e.getMessage());
        }
        return fgs;
    }
}
