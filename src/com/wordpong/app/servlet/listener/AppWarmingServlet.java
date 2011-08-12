package com.wordpong.app.servlet.listener;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wordpong.api.model.User;
import com.wordpong.api.svc.SvcCommonFactory;
import com.wordpong.api.svc.SvcGame;
import com.wordpong.api.svc.SvcGameFactory;
import com.wordpong.api.svc.SvcUser;
import com.wordpong.api.svc.SvcUserFactory;

@SuppressWarnings("serial")
public class AppWarmingServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(AppWarmingServlet.class.getName());

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isDatastoreUp = SvcCommonFactory.getSvcCommon().isDatastoreUp();
        if (isDatastoreUp) {
            SvcUser _svcUser = SvcUserFactory.getSvcUser();
            SvcGame _svcGame = SvcGameFactory.getSvcGame();
            long start = System.currentTimeMillis();
            User user = _svcUser.findByEmail("mike@systemsplanet.com");
            int size = -1;
            List<User> result = _svcGame.getMyFriends(user);
            if (result != null) {
                size = result.size();
            }
            log.info("WordPong Warmed up. elapsedMs:" + (System.currentTimeMillis() - start) + " friendCnt:" + size);
        } else {
            log.info("WordPong Warmed up. isDatastoreUp:" + isDatastoreUp);
        }
    }
}