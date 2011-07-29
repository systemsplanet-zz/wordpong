package com.wordpong.api.svc;

import com.wordpong.api.err.WPServiceException;
import com.wordpong.api.model.PasswordChangeRequest;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.DaoUser;
import com.wordpong.api.svc.dao.DaoUserFactory;
import com.wordpong.api.svc.dao.err.DaoException;

public class SvcUserImpl implements SvcUser {
    private DaoUser daoUser = DaoUserFactory.getDaoUser();

    
    // Create a unique user
    public User createUser(User u) throws WPServiceException {
        try {
            u = daoUser.createUser(u);
        } catch (DaoException e) {
            throw new WPServiceException(e.getMessage());
        }
        return u;
    }

    public User save(User u) throws WPServiceException {
        try {
            u = daoUser.save(u);
        } catch (DaoException e) {
            throw new WPServiceException(e.getMessage());
        }
        return u;
    }

    public User findByEmail(String email) throws WPServiceException {
        User result = null;
        try {
            result = daoUser.findByEmail(email);
        } catch (DaoException e) {
            throw new WPServiceException(e.getMessage());
        }
        return result;
    }

    public void purgeExpiredPasswordChangeRequests() {
        daoUser.purgeExpiredPasswordChangeRequests();
    }

    public String createPasswordChangeRequest(String email) throws WPServiceException {
        String result = null;
        try {
            result = daoUser.createPasswordChangeRequest(email);
        } catch (DaoException e) {
            throw new WPServiceException(e.getMessage());
        }
        return result;
    }

    public PasswordChangeRequest getPasswordChangeRequest(String randomId, String email) {
        PasswordChangeRequest result = daoUser.getPasswordChangeRequest(randomId, email);
        return result;
    }

    @Override
    public User getByKey(User u) throws WPServiceException {
        User result = null;
        try {
            result = daoUser.getUser(u);
        } catch (DaoException e) {
            throw new WPServiceException(e.getMessage());
        }
        return result;
    }
}
