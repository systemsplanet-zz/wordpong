package com.wordpong.api.svc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Key;
import com.google.common.base.Predicate;
import com.wordpong.api.model.FriendInvite;
import com.wordpong.api.model.FriendRequest;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.transact.Atomic;

public class DaoFriendUtil {
    private static final Logger log = Logger.getLogger(DaoFriendUtil.class.getName());

    public static void convertFriendInvitesToFriendRequests(User user, List<FriendInvite> invites) throws Exception {
        if (user != null && invites != null && invites.size() > 0) {
            List<Key> inviteKeys = new ArrayList<Key>();
            List<FriendRequest> requests = new ArrayList<FriendRequest>();
            for (FriendInvite invite : invites) {
                inviteKeys.add(invite.getKey());
                FriendRequest request = new FriendRequest(invite);
                requests.add(request);
            }
            final List<FriendRequest> fRequests = requests;
            final List<Key> fInviteKeys = inviteKeys;
            final User fUser = user;
            final String msg = "convertFriendInvitesToFriendRequests: user:" + user + " inviteKeys:" + inviteKeys + " requests:" + requests;
            Predicate<Atomic> WORK = new Predicate<Atomic>() {
                public boolean apply(Atomic at) {
                    log.info(msg);
                    // write new friend requests to database
                    List<Key> requestKeys = at.put(fRequests);
                    // add the keys to the users myTurn list
                    fUser.setInvitationRequests(requestKeys);
                    // write the user to the database
                    at.put(fUser);
                    // remove the source FriendInvites
                    at.delete(fInviteKeys);
                    return true;
                }

                public String toString() {
                    return msg;
                }
            };
            // may throw an exception if unable to process atomically
            int MAX_RETRIES = 5;
            Atomic.transact(WORK, MAX_RETRIES);
        }
    }

}
