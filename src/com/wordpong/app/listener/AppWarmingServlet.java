package com.wordpong.app.listener;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wordpong.cmn.svc.SvcCommonFactory;

@SuppressWarnings("serial")
public class AppWarmingServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(AppWarmingServlet.class.getName());

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isDatastoreUp = SvcCommonFactory.getSvcCommon().isDatastoreUp();
        log.info("WordPong Warmed up. isDatastoreUp:" + isDatastoreUp);
    }
}