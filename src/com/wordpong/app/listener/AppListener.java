package com.wordpong.app.listener;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppListener implements ServletContextListener {
	private static final Logger log = Logger
			.getLogger(AppListener.class.getName());

	public void contextInitialized(ServletContextEvent event) {
		// This will be invoked as part of a warming request, or the first user
		// request if no warming request was invoked.
		log.info("WordPong Context Initialized");
	}

	public void contextDestroyed(ServletContextEvent event) {
		// App Engine does not currently invoke this method.
	}
}