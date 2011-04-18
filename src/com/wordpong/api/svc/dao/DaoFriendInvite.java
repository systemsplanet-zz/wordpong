package com.wordpong.api.svc.dao;

import java.util.List;

import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;

public interface DaoFriendInvite {

    void inviteFriends(User user, List<String> emails) throws DaoException;;

    List<FriendInvite> getFriendInvitesByInviterKey(User user) throws DaoException;

    List<FriendInvite> getFriendInvitesByInviteeKey(User user) throws DaoException;

    List<FriendInvite> getFriendInvitesByEmail(User user) throws DaoException;

    List<FriendInvite> getAllFriendInvites() throws DaoException;

    void cancelInvitation(User user, String email) throws DaoException;

    void ignoreInvitation(String keyStr) throws DaoException;

}
