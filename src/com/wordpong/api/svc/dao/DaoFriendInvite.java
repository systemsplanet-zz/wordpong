package com.wordpong.api.svc.dao;

import java.util.List;

import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.User;

public interface DaoFriendInvite {

    void inviteFriends(User user, List<String> emails) throws DaoException;;

    List<FriendInvite> getFriendInvites(User user) throws DaoException;
    
    void cancelInvitation(User user, String email) throws DaoException;
    
}
