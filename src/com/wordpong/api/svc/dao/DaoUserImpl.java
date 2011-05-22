package com.wordpong.api.svc.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.wordpong.api.meta.PasswordChangeRequestMeta;
import com.wordpong.api.meta.UserMeta;
import com.wordpong.api.model.PasswordChangeRequest;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.err.DaoExceptionUserNotFound;
import com.wordpong.api.svc.dao.transact.Atomic;

public class DaoUserImpl extends DaoBase<User> implements DaoUser {
    private static final Logger log = Logger.getLogger(DaoUserImpl.class.getName());

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
            // catch (ConcurrentModificationException ex)?
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
        if (email == null) {
            throw new DaoException("cant find by null email");
        }
        User result = null;
        UserMeta e = UserMeta.get();
        try {
            result = Datastore.query(e).filter(e.email.equal(email)).asSingle();
        } catch (Exception ex) {
            // should never happen!
            // com.google.appengine.api.datastore.PreparedQuery$TooManyResultsException
            throw new DaoException("Err:" + ex.getMessage());
        }
        if (result == null) {
            throw new DaoExceptionUserNotFound("email:" + email);
        }
        return result;
    }

    public void makeFriends(Atomic at, final User invitee, final User inviter) throws DaoException {
        final String msg = "makeFriends: invitee:" + invitee + " inviter:" + inviter;
        try {
            Set<Key> u1Friends = invitee.getFriends();
            u1Friends.add(inviter.getKey());
            Set<Key> u2Friends = inviter.getFriends();
            u2Friends.add(invitee.getKey());
            at.put(invitee, inviter);
            log.finer(msg);
        } catch (Exception e) {
            log.warning(msg + " err:" + e.getMessage());
            throw new DaoException(e.getMessage());
        }
    }

    public User getUser(Key key) throws DaoException {
        User result = null;
        try {
            result = get(key);
        } catch (Exception e) {
            log.warning("getUser key:" + key + " err:" + e.getMessage());
            throw new DaoException(e.getMessage());
        }
        return result;
    }

    public void purgeExpiredPasswordChangeRequests() {
        log.info("purged Change Password Requests running");
        long TWO_HOURS = 1000L * 60L * 60L * 2L;
        Date twoHoursAgo = new Date(System.currentTimeMillis() - TWO_HOURS);
        PasswordChangeRequestMeta e = PasswordChangeRequestMeta.get();
        try {
            List<PasswordChangeRequest> pcrs = Datastore.query(e).filter(e.createdAt.lessThan(twoHoursAgo)).asList();
            if (pcrs != null && pcrs.size() > 0) {
                List<Key> keys = new ArrayList<Key>();
                for (PasswordChangeRequest pcr : pcrs) {
                    keys.add(pcr.getKey());
                }
                log.info("purging Change Password Requests: " + keys.size());
                Datastore.delete(keys);
            }
        } catch (Exception ex) {
            log.warning("purged Change Password Requests failed:" + ex.getMessage());
        }
    }

    // write a password change request to the data store
    public String createPasswordChangeRequest(String email) throws DaoException {
        Random generator = new Random();
        long unique = generator.nextLong();
        unique = Math.abs(unique);
        PasswordChangeRequest pcr = new PasswordChangeRequest();
        pcr.setEmail(email);
        String id = Long.toString(unique);
        pcr.setRandomId(id);
        Datastore.put(pcr);
        log.info("created PasswordChangeRequest:" + pcr);
        return id;
    }

    public PasswordChangeRequest getPasswordChangeRequest(String randomId, String email) {
        PasswordChangeRequest pcr = null;
        PasswordChangeRequestMeta e = PasswordChangeRequestMeta.get();
        try {
            pcr = Datastore.query(e).filter(e.randomId.equal(randomId)).filter(e.email.equal(email)).asSingle();
        } catch (Exception ex) {
            log.warning("unable to locate Change Password Request code:" + randomId + " email:" + email + " err:" + ex.getMessage());
        }
        return pcr;
    }

    @Override
    public List<User> getUsers(List<Key> keys) throws DaoException {
        List<User> result = new ArrayList<User>();
        try {
            result = get(keys);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
        return result;
    }

    @Override
    public User getUser(User u) throws DaoException {
        User result = null;
        try {
            if (u != null) {
                Key key = u.getKey();
                result = get(key);
            }
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
        return result;
    }

	@Override
	public List<User> getFriends(User u) throws DaoException {
		Set<Key> keySet = u.getFriends();
		List<Key> keyList = new ArrayList<Key>(keySet);
		List<User> us = getUsers(keyList);
		return us;
	}
}
