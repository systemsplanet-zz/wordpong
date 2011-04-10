package com.wordpong.api.svc;

import java.util.ArrayList;
import java.util.List;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.GameMyTurn;
import com.wordpong.api.pojo.GameMyTurn.Action;
import com.wordpong.api.svc.dao.DaoException;
import com.wordpong.api.svc.dao.DaoFriendInvite;
import com.wordpong.api.svc.dao.DaoFriendInviteFactory;

public class SvcGameImpl implements SvcGame {

    @Override
    public List<GameMyTurn> getMyTurns() {
        // TODO Call backend
        List<GameMyTurn> result = new ArrayList<GameMyTurn>();
        GameMyTurn gmt = new GameMyTurn();
        result.add(gmt);
        gmt = new GameMyTurn();
        gmt.setAction(Action.InvitationRequest);
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
            result = f.getFriendInvites(user);
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

}
