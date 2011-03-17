package com.wordpong.api.svc.dao;

import java.util.Set;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.wordpong.api.meta.UserMeta;
import com.wordpong.api.model.User;

public class DaoUserImpl extends DaoImpl<User> implements DaoUser {
    private static final Logger log = Logger.getLogger(DaoUserImpl.class.getName());

    static final String PROP_EMAIL = "email";

    // Create a user with a unique email address inside a transaction
    // since queries are not part of a transaction there is a chance of creating
    // duplicates!
    public User create(User u) throws DaoException {
        Transaction txn = Datastore.beginTransaction();
        try {
            findByEmail(u.getEmail());
        } catch (DaoExceptionUserNotFound e) {
            u = save(u);
            txn.commit();
        }
        return u;
    }

    public User save(User u) throws DaoException {
        if (u == null) {
            throw new DaoException("cant save null user");
        }
        try {
            String action = u.getKey() == null ? "Created" : "Updated";
            // Key key = Datastore.allocateId(User.class);
            // u.setKey(key);
            Key key = put(u);
            log.info(action + " user:" + u + " key:" + key);
        } catch (Exception e) {
            log.warning("unable to save user:" + u);
            throw new DaoException(e.getMessage());
        }
        return u;
    }


    public User findByEmail(String email) throws DaoException {
        User result = null;// = getByProperty(PROP_EMAIL, email);
        UserMeta e = UserMeta.get();
        try {
            result = Datastore.query(e).filter(e.email.equal(email)).asSingle();
        } catch (Exception ex) {
            // com.google.appengine.api.datastore.PreparedQuery$TooManyResultsException
            throw new DaoException("Err:" + ex.getMessage());
        }
        if (result == null) {
            throw new DaoExceptionUserNotFound("email:" + email);
        }
        return result;
    }

    public void makeFriends(User u1, User u2) throws DaoException {
    	Transaction txn = Datastore.beginTransaction();
        Set<Key> u1Friends = u1.getFriends();
        u1Friends.add(u2.getKey());
        Set<Key> u2Friends = u2.getFriends();
        u2Friends.add(u1.getKey());
        Datastore.put(u1, u2);
        txn.commit();
    }

    //TODO: add methods using delayed writes
    // private final DatastoreService ds =
    // DatastoreServiceFactory.getDatastoreService();
    // private final Queue delayedWriteQueue = QueueFactory.getDefaultQueue();


}
