package com.wordpong.api.svc.dao;

import com.google.appengine.api.datastore.Key;
import com.wordpong.api.model.PasswordChangeRequest;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public interface DaoUser {
    User save(User u) throws DaoException;

    User findByEmail(String email) throws DaoException;

    void makeFriends(Atomic at, Key user1, Key user2) throws DaoException;

    void purgeExpiredPasswordChangeRequests();

    public String createPasswordChangeRequest(String email) throws DaoException;

    public PasswordChangeRequest getPasswordChangeRequest(String randomId);

}
