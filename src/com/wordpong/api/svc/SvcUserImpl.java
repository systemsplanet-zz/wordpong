package com.wordpong.api.svc;

import java.util.logging.Logger;

import com.wordpong.api.model.PasswordChangeRequest;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.DaoUser;
import com.wordpong.api.svc.dao.DaoUserFactory;
import com.wordpong.api.svc.err.DaoException;
import com.wordpong.api.svc.err.WPServiceException;

public class SvcUserImpl implements SvcUser {
    private static final Logger log = Logger.getLogger(SvcUserImpl.class.getName());

    private DaoUser daoUser = DaoUserFactory.getDaoUser();

    // Create a unique user
    public User createUser(User u) throws WPServiceException {
        long start = System.currentTimeMillis();
        try {
            u = daoUser.createUser(u);
        } catch (DaoException e) {
            throw new WPServiceException("createUser user:" + u, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return u;
    }

    public User save(User u) throws WPServiceException {
        long start = System.currentTimeMillis();
        try {
            u = daoUser.save(u);
        } catch (DaoException e) {
            throw new WPServiceException("save user:" + u, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return u;
    }

    public User findByEmail(String email) throws WPServiceException {
        long start = System.currentTimeMillis();
        User result = null;
        try {
            result = daoUser.findByEmail(email);
        } catch (DaoException e) {
            throw new WPServiceException("findByEmail email:" + email, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    public void purgeExpiredPasswordChangeRequests() {
        long start = System.currentTimeMillis();
        try {
            daoUser.purgeExpiredPasswordChangeRequests();
        } catch (DaoException e) {
            throw new WPServiceException("purgeExpiredPasswordChangeRequests", e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
    }

    public String createPasswordChangeRequest(String email) throws WPServiceException {
        long start = System.currentTimeMillis();
        String result = null;
        try {
            result = daoUser.createPasswordChangeRequest(email);
        } catch (DaoException e) {
            throw new WPServiceException("createPasswordChangeRequest email" + email, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    public PasswordChangeRequest getPasswordChangeRequest(String randomId, String email) {
        long start = System.currentTimeMillis();
        PasswordChangeRequest result = null;
        try {
            result = daoUser.getPasswordChangeRequest(randomId, email);
        } catch (DaoException e) {
            throw new WPServiceException("getPasswordChangeRequest randomId:" + randomId + " email:" + email, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    public User getByKey(User u) throws WPServiceException {
        long start = System.currentTimeMillis();
        User result = null;
        try {
            result = daoUser.getUser(u);
        } catch (DaoException e) {
            throw new WPServiceException("getByKey user:"+u, e);
        } finally {
            log.fine("elapsedMs:" + (System.currentTimeMillis() - start));
        }
        return result;
    }
}
