package com.wordpong.api.svc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;

import com.google.appengine.api.datastore.Key;
import com.wordpong.api.model.FriendRequest;
import com.wordpong.api.model.User;

public class DaoFriendRequestImpl extends DaoBase<FriendRequest> implements DaoFriendRequest {
    private static final Logger log = Logger.getLogger(DaoFriendRequestImpl.class.getName());

    public List<FriendRequest> getFriendRequestsByKey(User user) {
        List<FriendRequest> result = new ArrayList<FriendRequest>();
        if (user != null && user.getInvitationRequests() != null) {
            List<Key> friendRequests = user.getInvitationRequests();
            try {
                result = get(friendRequests);
            } catch (Exception ex) {
                log.warning("Err:" + ex.getMessage());
            }
        }
        return result;
    }

}
