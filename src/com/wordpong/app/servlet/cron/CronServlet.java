package com.wordpong.app.servlet.cron;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.SvcUserFactory;
import com.wordpong.api.svc.err.WPServiceException;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CronServlet.class.getName());

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Scheduled task running.");

        // DEBUG - purge a table
        /*
        int BATCH = 100;
        log.info("UserFriend running:");
        // Delete all user firends
        List<UserFriend> result = null;
        UserFriendMeta e = UserFriendMeta.get();
        try {
            while (true) {
                result = Datastore.query(e).limit(BATCH).asList();
                log.info("UserFriend result:" + result);
                if (result != null && result.size() > 0) {
                    log.info("UserFriend result size:" + result.size());
                    List<Key> ks = new ArrayList<Key>();
                    for (int i = 0; i < BATCH; i++) {
                        ks.add(result.get(i).getKey());
                    }
                    log.info("UserFriend removing " + BATCH);
                    Datastore.delete(ks);
                } else {
                    break;
                }
            }
        } catch (Exception ex) {
            log.info("UserFriend Err:" + ex.getMessage());
        }
         */
        
        
        // Remove expired password change requests
        try {
            SvcUserFactory.getSvcUser().purgeExpiredPasswordChangeRequests();
        } catch (WPServiceException e) {
            e.printStackTrace();
        }

        // move any invites to the users myTurn list as requests
        SvcGameFactory.getSvcGame().updateFriendInvites();
    }
}