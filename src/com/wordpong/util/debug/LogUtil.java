package com.wordpong.util.debug;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class LogUtil {
	private static final Logger log = Logger.getLogger(LogUtil.class.getName());

	public static void logException(String reason, Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		log.severe("reason:" + reason + "\n" + sw.toString());
	}
}
