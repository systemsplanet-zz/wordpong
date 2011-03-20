package com.wordpong.api.svc.dao;

import com.wordpong.api.model.PasswordChangeRequest;
import com.wordpong.api.model.User;

public interface DaoUser {
    User save(User u) throws DaoException;

    User findByEmail(String email) throws DaoException;

    void makeFriends(User u1, User u2) throws DaoException;

    void purgeExpiredPasswordChangeRequests();

    public String createPasswordChangeRequest(String email) throws DaoException;

    public PasswordChangeRequest getPasswordChangeRequest(String randomId);

}
