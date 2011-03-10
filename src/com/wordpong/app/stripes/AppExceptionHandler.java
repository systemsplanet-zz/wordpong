package com.wordpong.app.stripes;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.exception.ActionBeanNotFoundException;
import net.sourceforge.stripes.exception.ExceptionHandler;

import com.wordpong.cmn.util.debug.LogUtil;

public class AppExceptionHandler implements ExceptionHandler {
	private static final Logger log = Logger
			.getLogger(AppExceptionHandler.class.getName());

	/** Doesn't have to do anything... */
	public void init(Configuration configuration) throws Exception {
	}

	/** Do something a bit more complicated that just going to a view. */
	public void handle(Throwable throwable, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ERR_GENERAL = "/err/app_error.html";
		String ERR_NOT_FOUND = "/err/notfound.html";
		String resolution = ERR_GENERAL;
		// TODO: rollback any transactions?
		log.warning("exception: " + throwable.getMessage());
		if (throwable instanceof ActionBeanNotFoundException) {
			resolution = ERR_NOT_FOUND;
		} else {
			LogUtil.logException("uncaught app exception", throwable);
			request.setAttribute("exception", throwable);
		}
		// goto error page
		request.getRequestDispatcher(resolution).forward(request, response);
	}

}