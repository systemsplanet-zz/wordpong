package com.wordpong.util.debug;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
/**
 * Class to print console messages in eclipse
 * Add to logging.properties:
 *   java.util.logging.ConsoleHandler.level=FINE
 *   java.util.logging.ConsoleHandler.formatter=com.wordpong.util.debug.LogFormatter
 *   http://blog.xam.de/2010/03/logging-in-google-appengine-for-java.html
 */
public class LogFormatter extends SimpleFormatter {
	private SimpleDateFormat s;
	private StringBuffer sb;

	public LogFormatter() {
		super();
		s = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss.SSS, ");
	}

	public String format(LogRecord r) {
		sb = new StringBuffer(s.format(new Date(r.getMillis())));
		sb.append(r.getLevel().toString());
		sb.append(", ");
		sb.append(r.getSourceClassName());
		sb.append(".");
		sb.append(r.getSourceMethodName());
		sb.append("(), "); 
		sb.append(r.getMessage().replaceAll("\"", "'"));
		sb.append("\n");
		return sb.toString();
	}
}
