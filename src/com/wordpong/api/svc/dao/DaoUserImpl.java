package com.wordpong.api.svc.dao;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.wordpong.api.meta.UserMeta;
import com.wordpong.api.model.User;
import com.wordpong.app.action.RegisterActionBean;

public class DaoUserImpl implements DaoUser {
    private static final Logger log = Logger.getLogger(RegisterActionBean.class.getName());

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

    // Key key = u.getKey();
    public User read(Key key) {
        User u = Datastore.get(User.class, key);
        return u;
    }

    public List<User> readList(List<Key> keys) {
        List<User> us = Datastore.get(User.class, keys);
        return us;
    }

    public Future<List<User>> readListAsync(List<Key> keys) {
        Future<List<User>> futures = Datastore.getAsync(User.class, keys);
        return futures;
    }

    public void update(User u) {
        Datastore.put(u);
    }

    // use Key key= future.get() to get the result
    public Future<Key> updateAsync(User u) {
        Future<Key> future = Datastore.putAsync(u);
        return future;
    }

    public void updateList(List<User> us) {
        Datastore.put(us);
    }

    public void delete(Key k) {
        Datastore.delete(k);
    }

    // use future.get() to get the result
    public Future<Void> deleteAsync(Key k) {
        Future<Void> future = Datastore.deleteAsync(k);
        return future;
    }

    public void deleteList(List<Key> ks) {
        Datastore.delete(ks);
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
            String action = u.getKey() == null ? "Created" : "Updated";
            // Key key = Datastore.allocateId(User.class);
            // u.setKey(key);
            Key key = Datastore.put(u);
            //u.setKey(key);
            log.info(action + " user:" + u + " key:" + key);
            // TODO: if u.id is not set, set it here
        } catch (Exception e) {
            log.warning("unable to save user:" + u);
            throw new DaoException(e.getMessage());
        }
        return u;
    }

    // use Key key = future.get(); to get result
    public Future<Key> saveAsync(User u) throws DaoException {
        Future<Key> future = Datastore.putAsync(u);
        return future;
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
        /*
         * result.setPassword(Encrypt.hashSha1("test"));
         */
        if (result == null) {
            throw new DaoExceptionUserNotFound("email:" + email);
        }
        return result;
    }

    public void makeFriends(User u1, User u2) throws DaoException {
        Set<Key> u1Friends = u1.getFriends();
        u1Friends.add(u2.getKey());
        Set<Key> u2Friends = u2.getFriends();
        u2Friends.add(u1.getKey());
        Datastore.put(u1, u2);
    }
}
