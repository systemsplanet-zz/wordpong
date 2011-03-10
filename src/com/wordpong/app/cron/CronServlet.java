package com.wordpong.app.cron;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(CronServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		log.info("Scheduled task ran.");
	}
}