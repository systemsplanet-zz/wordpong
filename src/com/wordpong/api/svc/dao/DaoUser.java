package com.wordpong.api.svc.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.wordpong.api.model.PasswordChangeRequest;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public interface DaoUser {
    User save(User u) throws DaoException;

    User findByEmail(String email) throws DaoException;

    void makeFriends(Atomic at, User invitee, User inviter) throws DaoException;

    void purgeExpiredPasswordChangeRequests();

    String createPasswordChangeRequest(String email) throws DaoException;

    PasswordChangeRequest getPasswordChangeRequest(String randomId);

    List<User> getUsers(List<Key> keys) throws DaoException;

    User getUser(Key key) throws DaoException;

    User getUser(User user) throws DaoException;

}
