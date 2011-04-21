package com.wordpong.api.svc.dao;

import java.util.List;

import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public interface DaoFriendInvite {

    void inviteFriends(User user, List<String> emails) throws DaoException;;

    List<InviteFriend> getFriendInvitesByInviterKey(User user) throws DaoException;

    List<InviteFriend> getFriendInvitesByInviteeKey(User user) throws DaoException;

    List<InviteFriend> getFriendInvitesByEmail(User user) throws DaoException;

    List<InviteFriend> getAllFriendInvites() throws DaoException;

    void cancelInvitation(User user, String email) throws DaoException;

    void ignoreInvitation(String friendInviteKeyStr) throws DaoException;

    InviteFriend toFriendInvite(String friendInviteKeyStr) throws DaoException;

    void removeInvitation(Atomic at, InviteFriend fi) throws DaoException;
}
