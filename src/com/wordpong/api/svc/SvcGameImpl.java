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
import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.FriendGames;
import com.wordpong.api.pojo.GameMyTurn;
import com.wordpong.api.pojo.GameTheirTurn;
import com.wordpong.api.svc.dao.DaoFriendInvite;
import com.wordpong.api.svc.dao.DaoFriendInviteFactory;
import com.wordpong.api.svc.dao.DaoQuestion;
import com.wordpong.api.svc.dao.DaoQuestionFactory;
import com.wordpong.api.svc.dao.DaoUser;
import com.wordpong.api.svc.dao.DaoUserFactory;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public class SvcGameImpl implements SvcGame {
    private static final Logger log = Logger.getLogger(SvcGameImpl.class.getName());

    @Override
    public List<GameMyTurn> getMyTurns(User user) {
        List<GameMyTurn> result = new ArrayList<GameMyTurn>();
        DaoFriendInvite dfr = DaoFriendInviteFactory.getFriendInviteDao();

        try {
            List<FriendInvite> requests = dfr.getFriendInvitesByInviteeKey(user);
            for (FriendInvite fr : requests) {
                if (fr.isIgnored() == false) {
                    GameMyTurn gmt = new GameMyTurn();
                    gmt.setAction(GameMyTurn.Action.InvitationRequest);
                    gmt.setId(fr.getInviterEmail());
                    gmt.setDetails(fr.getInviterDetails());
                    gmt.setCreatedAtString(fr.getCreatedAtString());
                    gmt.setKey(fr.getKeyString());
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
        DaoFriendInvite dfr = DaoFriendInviteFactory.getFriendInviteDao();
        try {
            List<FriendInvite> invites = dfr.getFriendInvitesByInviterKey(user);
            if (invites != null) {
                for (FriendInvite fi : invites) {
                    GameTheirTurn gtt = new GameTheirTurn();
                    gtt.setId(fi.getInviteeEmail());
                    gtt.setDetails(fi.getInviteeDetails());
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
    // List<FriendInvite> getFriendInvitesByKey(User user)
    // TODO call backend

    // }

    public void inviteFriends(User user, List<String> emails) throws WPServiceException {
        DaoFriendInvite f = DaoFriendInviteFactory.getFriendInviteDao();
        try {
            f.inviteFriends(user, emails);
        } catch (DaoException e) {
            throw new WPServiceException("inviteFriends err: " + e.getMessage());
        }
    }

    @Override
    public List<FriendInvite> getFriendInvitesByInviterKey(User user) throws WPServiceException {
        List<FriendInvite> result = new ArrayList<FriendInvite>();
        DaoFriendInvite f = DaoFriendInviteFactory.getFriendInviteDao();
        try {
            result = f.getFriendInvitesByInviterKey(user);
        } catch (DaoException e) {
            throw new WPServiceException("inviteFriends err: " + e.getMessage());
        }
        return result;
    }

    @Override
    public void cancelInvitation(User user, String email) throws WPServiceException {
        DaoFriendInvite f = DaoFriendInviteFactory.getFriendInviteDao();
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
     * * Scan the FriendInvitesTable for a user's email. For each FriendInvite
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
                        DaoFriendInvite dfi = DaoFriendInviteFactory.getFriendInviteDao();
                        List<FriendInvite> invites = dfi.getFriendInvitesByEmail(fUser);
                        final String msg = "updateFriendInvites: user:" + fUser + " invites:" + invites;
                        log.info(msg);
                        Key key = fUser.getKey();
                        String details = fUser.getDetails();
                        if (invites != null && invites.size() > 0) {
                            for (FriendInvite invite : invites) {
                                invite.setInviteeKey(key);
                                invite.setInviteeDetails(details);
                            }
                            // write new friend requests to database
                            at.put(invites);
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
    }

    // cron calls this to move any missed invites to the users myTurn list
    public void updateFriendInvites() {
        DaoFriendInvite dfi = DaoFriendInviteFactory.getFriendInviteDao();
        try {
            List<FriendInvite> invites = dfi.getAllFriendInvites();
            Map<String, Boolean> processed = new HashMap<String, Boolean>();
            if (invites != null) {
                DaoUser du = DaoUserFactory.getUserDao();
                for (FriendInvite fi : invites) {
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
        DaoFriendInvite dfi = DaoFriendInviteFactory.getFriendInviteDao();
        try {
            dfi.ignoreInvitation(friendInvitekeyStr);
        } catch (DaoException e) {
            throw new WPServiceException(e.getMessage());
        }

    }

    public void makeFriends(final String friendInviteKeyStr) throws WPServiceException {
        final DaoFriendInvite dfi = DaoFriendInviteFactory.getFriendInviteDao();
        final DaoUser du = DaoUserFactory.getUserDao();
        final String msg = "makeFriends friendInviteKeyStr" + friendInviteKeyStr;
        FriendInvite fi = null;
        try {
            fi = dfi.toFriendInvite(friendInviteKeyStr);
        } catch (DaoException e1) {
            throw new WPServiceException(e1.getMessage());
        }
        final FriendInvite ffi = fi;
        Predicate<Atomic> WORK = new Predicate<Atomic>() {
            public boolean apply(Atomic at) {
                boolean result = false;
                try {
                    Key inviteeKey = ffi.getInviteeKey();
                    Key inviterKey = ffi.getInviterKey();
                    du.makeFriends(at, inviteeKey, inviterKey);
                    dfi.removeInvitation(at, ffi);
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
