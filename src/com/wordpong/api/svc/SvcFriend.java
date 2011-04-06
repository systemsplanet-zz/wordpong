package com.wordpong.api.svc;

import java.util.List;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.User;

public interface SvcFriend {
    void inviteFriends(User user, List<String> emails) throws WPServiceException;

    List<FriendInvite> getFriendInvites(User user) throws WPServiceException;
}
