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
import com.wordpong.api.meta.InviteFriendMeta;
import com.wordpong.api.model.InviteFriend;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public class DaoInviteFriendImpl extends DaoBase<InviteFriend> implements DaoInviteFriend {
    private static final Logger log = Logger.getLogger(DaoInviteFriendImpl.class.getName());

    @Override
    public void inviteFriends(User user, List<String> emails) throws DaoException {
        Transaction txn = Datastore.beginTransaction();
        if (user != null && emails != null) {
            for (String email : emails) {
                InviteFriend fi = new InviteFriend();
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
    public List<InviteFriend> getFriendInvitesByInviterKey(User user) throws DaoException {
        List<InviteFriend> result = null;
        InviteFriendMeta e = InviteFriendMeta.get();

        try {
            Key k = user.getKey();
            result = Datastore.query(e).filter(e.inviterKey.equal(k)).asList();

        } catch (Exception ex) {
            throw new DaoException("Err:" + ex.getMessage());
        }
        return result;
    }

    @Override
    public List<InviteFriend> getFriendInvitesByInviteeKey(User user) throws DaoException {
        List<InviteFriend> result = null;
        InviteFriendMeta e = InviteFriendMeta.get();

        try {
            Key k = user.getKey();
            result = Datastore.query(e).filter(e.inviteeKey.equal(k)).asList();

        } catch (Exception ex) {
            throw new DaoException("Err:" + ex.getMessage());
        }
        return result;
    }

    @Override
    public List<InviteFriend> getFriendInvitesByEmail(User user) throws DaoException {
        List<InviteFriend> result = new ArrayList<InviteFriend>();
        if (user != null && user.getEmail() != null) {
            InviteFriendMeta e = InviteFriendMeta.get();
            try {
                result = Datastore.query(e).filter(e.inviteeDetails.equal(user.getEmail())).asList();
            } catch (Exception ex) {
                throw new DaoException("Err:" + ex.getMessage());
            }
        }
        return result;
    }

    @Override
    public List<InviteFriend> getAllFriendInvites() throws DaoException {
        List<InviteFriend> result = null;
        InviteFriendMeta e = InviteFriendMeta.get();
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
            List<InviteFriend> invites = getFriendInvitesByInviterKey(user);
            if (invites != null) {
                for (InviteFriend i : invites) {
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

    public void ignoreInvitation(String friendInviteKeyStr) throws DaoException {
        if (friendInviteKeyStr == null)
            throw new DaoException("key cant be null");
        final Key k = KeyFactory.stringToKey(friendInviteKeyStr);
        try {
            final String msg = "ignoreInvitation: keyStr:" + friendInviteKeyStr + " key:" + k;
            Predicate<Atomic> WORK = new Predicate<Atomic>() {
                public boolean apply(Atomic at) {
                    boolean result = false;
                    try {
                        InviteFriend fi = get(k);
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
            Atomic.transact(WORK);
        } catch (Exception e) {
            String m = "ignoreInvitation failed. keyStr:" + friendInviteKeyStr + " Err:" + e.getMessage();
            log.warning(m);
            throw new DaoException(m);
        }
    }

    public InviteFriend getFriendInvite(String friendInviteKeyStr) throws DaoException {
        InviteFriend result = null;
        try {
            if (friendInviteKeyStr == null)
                throw new DaoException("friendInviteKeyStr cant be null");
            Key k = KeyFactory.stringToKey(friendInviteKeyStr);
            result = get(k);
        } catch (Exception e) {
            String m = "toFriendInvite failed. keyStr:" + friendInviteKeyStr + " Err:" + e.getMessage();
            log.warning(m);
            throw new DaoException(m);
        }
        return result;
    }

    @Override
    public void removeInvitation(Atomic at, InviteFriend fi) throws DaoException {
        String m = "removeInvitation. InviteFriend:" + fi;
        log.fine(m);
        try {
            if (fi != null) {
                Key k = fi.getKey();
                if (k != null && k.isComplete()) {                    
                    at.delete(k);
                }
            }
        } catch (Exception e) {
            log.warning(m + " Err:" + e.getMessage());
            throw new DaoException(m);
        }
    }
}
