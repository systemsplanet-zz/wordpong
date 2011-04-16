package com.wordpong.api.svc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.GameMyTurn;
import com.wordpong.api.pojo.GameMyTurn.Action;
import com.wordpong.api.svc.dao.DaoFriendInvite;
import com.wordpong.api.svc.dao.DaoFriendInviteFactory;
import com.wordpong.api.svc.dao.DaoFriendUtil;
import com.wordpong.api.svc.dao.DaoQuestion;
import com.wordpong.api.svc.dao.DaoQuestionFactory;
import com.wordpong.api.svc.dao.DaoUser;
import com.wordpong.api.svc.dao.DaoUserFactory;
import com.wordpong.api.svc.dao.err.DaoException;

public class SvcGameImpl implements SvcGame {
    private static final Logger log = Logger.getLogger(SvcGameImpl.class.getName());

    @Override
    public List<GameMyTurn> getMyTurns() {
        // TODO Call backend
        List<GameMyTurn> result = new ArrayList<GameMyTurn>();
        GameMyTurn gmt = new GameMyTurn();
        result.add(gmt);
        gmt = new GameMyTurn();
        gmt.setAction(Action.InvitationRequest);
        // gmt.setCreatedAtString(createdAtString)
        result.add(gmt);
        gmt = new GameMyTurn();
        gmt.setAction(Action.InviteAccepted);
        result.add(gmt);
        return result;
    }

    @Override
    public void setMyTurns(List<GameMyTurn> myTurns) {
        // TODO call backend

    }

    public void inviteFriends(User user, List<String> emails) throws WPServiceException {
        DaoFriendInvite f = DaoFriendInviteFactory.getFriendInviteDao();
        try {
            f.inviteFriends(user, emails);
        } catch (DaoException e) {
            throw new WPServiceException("inviteFriends err: " + e.getMessage());
        }
    }

    @Override
    public List<FriendInvite> getFriendInvites(User user) throws WPServiceException {
        List<FriendInvite> result = new ArrayList<FriendInvite>();
        DaoFriendInvite f = DaoFriendInviteFactory.getFriendInviteDao();
        try {
            result = f.getFriendInvitesByKey(user);
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
     * * Scan the FriendInvitesTable for a user's email (or all users if user is
     * null) For each FriendInvite Try to find an existing user with that email
     * If found, convert the FriendInvite to a FriendRequest and add to the
     * users myTurn list
     */
    public void convertFriendInvitesToFriendRequests(User user) throws WPServiceException {
        if (user != null) {
            DaoFriendInvite dfi = DaoFriendInviteFactory.getFriendInviteDao();
            try {
                // get FriendInvites for this user from db
                List<FriendInvite> invites = dfi.getFriendInvitesByEmail(user);
                DaoFriendUtil.convertFriendInvitesToFriendRequests(user, invites);
            } catch (Exception e) {
                throw new WPServiceException(e.getMessage());
            }
        }
    }

    // cron calls this to move any missed invites to the users myTurn list
    public void convertAllFriendInvitesToFriendRequests() {
        DaoFriendInvite dfi = DaoFriendInviteFactory.getFriendInviteDao();
        try {
            List<FriendInvite> invites = dfi.getAllFriendInvites();
            if (invites != null) {
                DaoUser du = DaoUserFactory.getUserDao();
                for (FriendInvite fi : invites) {
                    try {
                        String email = fi.getInviteeEmail();
                        User user = du.findByEmail(email);
                        List<FriendInvite> usersInvites = new ArrayList<FriendInvite>();
                        usersInvites.add(fi);
                        // todo: enhancement- group invites together for this
                        // user
                        DaoFriendUtil.convertFriendInvitesToFriendRequests(user, usersInvites);
                    } catch (DaoException e1) {
                        // ignore user not found errors
                    }
                }
            }
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }
}
