package com.wordpong.api.svc.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.wordpong.api.model.PasswordChangeRequest;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.err.DaoException;

public interface DaoUser {
    User createUser(User user) throws DaoException;

    User save(User u) throws DaoException;

    User save(Atomic at, User u) throws DaoException;

    User findByEmail(String email) throws DaoException;

    void makeFriends(Atomic at, User invitee, User inviter) throws DaoException;

    void purgeExpiredPasswordChangeRequests();

    String createPasswordChangeRequest(String email) throws DaoException;

    PasswordChangeRequest getPasswordChangeRequest(String randomId, String email);

    List<User> getUsers(List<Key> keys) throws DaoException;

    List<User> getUsersByKeyStrings(List<String> keys) throws DaoException;

    List<User> getFriends(User u) throws DaoException;

    List<String> getFriendsKeyStrings(User u) throws DaoException;

    User getUser(Key key) throws DaoException;

    User getUser(User user) throws DaoException;

    User getUser(Atomic at, User user) throws DaoException;

    User getUser(Atomic at, String uk) throws DaoException;

}
