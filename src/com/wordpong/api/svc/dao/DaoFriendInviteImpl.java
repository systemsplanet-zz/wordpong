package com.wordpong.api.svc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.common.base.Predicate;
import com.wordpong.api.meta.FriendInviteMeta;
import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public class DaoFriendInviteImpl extends DaoBase<FriendInvite> implements DaoFriendInvite {
    private static final Logger log = Logger.getLogger(DaoFriendInviteImpl.class.getName());

    @Override
    public void inviteFriends(User user, List<String> emails) throws DaoException {
        Transaction txn = Datastore.beginTransaction();
        if (user != null && emails != null) {
            for (String email : emails) {
                FriendInvite fi = new FriendInvite();
                fi.setInviteeDetails(email);
                fi.setInviterKey(user.getKey());
                fi.setInviterDetails(user.getDetails());
                Key key = put(fi);
                log.info("invite email:" + email + " for user:" + user + " key:" + key);
            }
        }
        txn.commit();
    }

    @Override
    public List<FriendInvite> getFriendInvitesByInviterKey(User user) throws DaoException {
        List<FriendInvite> result = null;
        FriendInviteMeta e = FriendInviteMeta.get();

        try {
            Key k = user.getKey();
            result = Datastore.query(e).filter(e.inviterKey.equal(k)).asList();

        } catch (Exception ex) {
            throw new DaoException("Err:" + ex.getMessage());
        }
        return result;
    }

    @Override
    public List<FriendInvite> getFriendInvitesByInviteeKey(User user) throws DaoException {
        List<FriendInvite> result = null;
        FriendInviteMeta e = FriendInviteMeta.get();

        try {
            Key k = user.getKey();
            result = Datastore.query(e).filter(e.inviteeKey.equal(k)).asList();

        } catch (Exception ex) {
            throw new DaoException("Err:" + ex.getMessage());
        }
        return result;
    }

    @Override
    public List<FriendInvite> getFriendInvitesByEmail(User user) throws DaoException {
        List<FriendInvite> result = new ArrayList<FriendInvite>();
        if (user != null && user.getEmail() != null) {
            FriendInviteMeta e = FriendInviteMeta.get();
            try {
                result = Datastore.query(e).filter(e.inviteeDetails.equal(user.getEmail())).asList();
            } catch (Exception ex) {
                throw new DaoException("Err:" + ex.getMessage());
            }
        }
        return result;
    }

    @Override
    public List<FriendInvite> getAllFriendInvites() throws DaoException {
        List<FriendInvite> result = null;
        FriendInviteMeta e = FriendInviteMeta.get();
        try {
            result = Datastore.query(e).asList();
        } catch (Exception ex) {
            throw new DaoException("Err:" + ex.getMessage());
        }
        return result;
    }

    @Override
    public void cancelInvitation(User user, String email) throws DaoException {
        if (user != null && email != null) {
            List<FriendInvite> invites = getFriendInvitesByInviterKey(user);
            if (invites != null) {
                for (FriendInvite i : invites) {
                    Key key = user.getKey();
                    if (i.getInviterKey().equals(key)) {
                        try {
                            Key k = i.getKey();
                            if (k != null) {
                                delete(k);
                                log.info("cancelInvitation:" + i);
                            }
                        } catch (Exception ex) {
                            String m = "cancelInvitation failed. email:" + email + " from:" + user + " Err:" + ex.getMessage();
                            log.warning(m);
                            throw new DaoException(m);
                        }
                        break;
                    }
                }
            }
        }
    }

    public void ignoreInvitation(String keyStr) throws DaoException {
        if (keyStr == null)
            throw new DaoException("key cant be null");
        final Key k = KeyFactory.stringToKey(keyStr);
        try {
            final String msg = "ignoreInvitation: keyStr:" + keyStr + " key:" + k;
            Predicate<Atomic> WORK = new Predicate<Atomic>() {
                public boolean apply(Atomic at) {
                    boolean result = false;
                    try {
                        FriendInvite fi = get(k);
                        fi.setIgnored(true);
                        put(fi);
                        result = true;
                    } catch (Exception e) {
                    }
                    return result;
                }

                public String toString() {
                    return msg;
                }
            };
            int MAX_RETRIES = 5;
            Atomic.transact(WORK, MAX_RETRIES);
        } catch (Exception e) {
            String m = "ignoreInvitation failed. keyStr:" + keyStr + " Err:" + e.getMessage();
            log.warning(m);
            throw new DaoException(m);
        }
    }

}
