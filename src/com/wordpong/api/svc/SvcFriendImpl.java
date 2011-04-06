package com.wordpong.api.svc;

import java.util.ArrayList;
import java.util.List;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.DaoException;
import com.wordpong.api.svc.dao.DaoFriendInvite;
import com.wordpong.api.svc.dao.DaoFriendInviteFactory;

public class SvcFriendImpl implements SvcFriend {
    @Override
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

}
