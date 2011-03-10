package com.wordpong.api.svc.dao;

import java.util.logging.Logger;

import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.wordpong.api.model.User;
import com.wordpong.app.action.RegisterActionBean;
import com.wordpong.cmn.db.objectify.ObjectifyGenericDao;

public class DaoUserImpl extends ObjectifyGenericDao<User> implements DaoUser {
    private static final Logger log = Logger.getLogger(RegisterActionBean.class.getName());

    static {
        // Register all your entity classes here
        ObjectifyService.register(User.class);
    }

    static final String PROP_EMAIL = "email";

    // Create a user with a unique email address inside a transaction
    // since queries are not part of a transaction there is a chance of creating
    // duplicates!
    public User create(User u) throws DaoException {
        Transaction txn = this.ofy().getTxn();
        try {
            findByEmail(u.getEmail());
        } catch (DaoExceptionUserNotFound e) {
            u = save(u);
            ofy().getTxn().commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
        return u;
    }

    // private final DatastoreService ds =
    // DatastoreServiceFactory.getDatastoreService();
    // private final Queue delayedWriteQueue = QueueFactory.getDefaultQueue();

    // Encrypts the password if unencrypted
    public User save(User u) throws DaoException {
        if (u == null) {
            throw new DaoException("cant save null user");
        }
        try {
            String action = u.getId() == null ? "Created" : "Updated";
            Key<User> key = this.put(u);
            log.info(action + " user:" + u + " key:" + key);
            // TODO: if u.id is not set, set it here
        } catch (Exception e) {
            log.warning("unable to save user:" + u);
            throw new DaoException(e.getMessage());
        }
        return u;
    }

    public User findByEmail(String email) throws DaoException {
        User result = getByProperty(PROP_EMAIL, email);
        /*
         * // TODO: call backend to find user if (email.equals("test@test.com"))
         * { result = new User(); result.setFirstName("John");
         * result.setLastName("Doe");
         * result.setPassword(Encrypt.hashSha1("test"));
         * result.setActivated(true); }
         */
        if (result == null) {
            throw new DaoExceptionUserNotFound("email:" + email);
        }
        return result;
    }

}
