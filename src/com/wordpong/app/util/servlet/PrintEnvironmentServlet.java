package com.wordpong.app.util.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.SimpleTimeZone;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.ResponseTooLargeException;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.google.apphosting.api.ApiProxy;

@SuppressWarnings("serial")
public class PrintEnvironmentServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(PrintEnvironmentServlet.class.getName());

	public static String escapeHtmlChars(String inStr) {
		return inStr.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	@SuppressWarnings({ "rawtypes" })
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String[] nameArray;

		out.println("<ul>" + "<li><a href=\"#user\">User Service</a></li>"
				+ "<li><a href=\"#env\">Environment Variables</a></li>"
				+ "<li><a href=\"#props\">System Properties</a></li>"
				+ "<li><a href=\"#requestd\">Request Data</a></li>"
				+ "<li><a href=\"#requesta\">Request Attributes</a></li>"
				+ "<li><a href=\"#requestp\">Request Params</a></li>"
				+ "<li><a href=\"#requestu\">Request Utils</a></li>"

				+ "</ul>");
		out.println("<hr noshade><h2 id=\"user\">User Service</h2><table>");

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			String logoutUrl = userService.createLogoutURL("/");
			out.println("Logout URL:[" + logoutUrl + "] <a href=\"" + logoutUrl
					+ "\">sign out</a>.<br>");
			out.println("Auth Domain:[" + user.getAuthDomain() + "]<br>");
			out.println("Email:[" + user.getEmail() + "]<br>");
			out.println("Federated Identity:[" + user.getFederatedIdentity()
					+ "]<br>");
			out.println("Nick Name:[" + user.getNickname() + "]<br>");
			out.println("User Id:[" + user.getUserId() + "]<br>");
			Principal p = req.getUserPrincipal();
			if (p != null) {
				out.println("Principal Name:[" + p.getName() + "]<br>");
			}
		} else {
			String loginUrl = userService.createLoginURL("/");
			out.println("Loging URL:[" + loginUrl + "] <a href=\"" + loginUrl
					+ "\">Sign in</a>.</p>");
		}

		out.println("<hr noshade><h2 id=\"servlet\">"
				+ "Servlet Information</h2><table>");
		out.println("<tr><td valign=\"top\">"
				+ "this.getServletContext().getServerInfo()</td>"
				+ "<td valign=\"top\">"
				+ escapeHtmlChars(this.getServletContext().getServerInfo())
				+ "</td></tr>");
		out.println("<tr><td>SystemProperty.environment</td>");
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			out.println("<td>SystemProperty.Environment.Value.Production</td>");
		} else {
			out.println("<td>SystemProperty.Environment"
					+ ".Value.Development</td>");
		}
		out.println("</tr>");
		out.println("<tr><td>SystemProperty.version</td><td><code>"
				+ escapeHtmlChars(SystemProperty.version.get())
				+ "</code></td></tr>");
		out.println("</table>");
		// add servlet context attributes
		// add servlet context init parameters

		out.println("<hr noshade><h2 id=\"env\">"
				+ "Environment Variables</h2><table>");
		Map<String, String> environment = System.getenv();
		nameArray = environment.keySet().toArray(new String[] {});
		Arrays.sort(nameArray);
		for (String name : nameArray) {
			out.println("<tr><td valign=\"top\"><code>" + escapeHtmlChars(name)
					+ "</code></td><td valign=\"top\"><code>"
					+ escapeHtmlChars(environment.get(name))
					+ "</code></td></tr>");
		}
		out.println("</table>");

		out.println("<hr noshade><h2 id=\"props\">"
				+ "System Properties</h2><table>");
		Properties systemProperties = System.getProperties();
		nameArray = Collections.list(systemProperties.propertyNames()).toArray(
				new String[] {});
		Arrays.sort(nameArray);
		for (String name : nameArray) {
			out.println("<tr><td valign=\"top\"><code>" + escapeHtmlChars(name)
					+ "</code></td><td valign=\"top\"><code>"
					+ escapeHtmlChars(systemProperties.getProperty(name))
					+ "</code></td></tr>");
		}
		out.println("</table>");

		// out.println("<hr noshade><h2 id=\"filesystem\">File System</h2><pre>");
		// printDirectoryListing(out, new File("."), "");
		// out.println("</pre>");

		out.println("<hr noshade><h2 id=\"requestd\">Request Data</h2><pre>");
		BufferedReader reader = req.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			out.println(escapeHtmlChars(line));
		}
		out.println("</pre>");

		// servlet request attributes
		out.println("<hr noshade><h2 id=\"requesta\">"
				+ "Request Attributes</h2><pre>");
		Enumeration e = req.getAttributeNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			Object val = req.getAttribute(key);
			if (val instanceof String) {
				out.println("[" + key + "]=[" + val + "]");
			}
		}
		out.println("</pre>");

		// request parameters
		out.println("<hr noshade><h2 id=\"requestp\">"
				+ "Request Parameters</h2><pre>");
		e = req.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			Object val = req.getParameter(key);
			if (val instanceof String) {
				out.println("[" + key + "]=[" + val + "]");
			}
		}
		out.println("</pre>");

		// request utils
		out.println("<hr noshade><h2 id=\"requestu\">" + "Request Utils</h2>");
		out.println("Req Admin Role:[" + req.isUserInRole("admin") + "]<br>");
		out.println("Req Auth Type:[" + req.getAuthType() + "]<br>");
		out.println("Req Char Encoding:[" + req.getCharacterEncoding()
				+ "]<br>");
		out.println("Req Content Len:[" + req.getContentLength() + "]<br>");
		out.println("Req Content Type:[" + req.getContentType() + "]<br>");
		out.println("Req Context Path:[" + req.getContextPath() + "]<br>");
		out.println("Req Local Addr:[" + req.getLocalAddr() + "]<br>");
		out.println("Req Local Name:[" + req.getLocalName() + "]<br>");
		out.println("Req Local Port:[" + req.getLocalPort() + "]<br>");
		out.println("Req Method:[" + req.getMethod() + "]<br>");
		out.println("Req Path Info:[" + req.getPathInfo() + "]<br>");
		out.println("Req Path" + " Translated:[" + req.getPathTranslated()
				+ "]<br>");
		out.println("Req Protocol:[" + req.getProtocol() + "]<br>");
		out.println("Req Query String :[" + req.getQueryString() + "]<br>");
		out.println("Req Remote Addr:[" + req.getRemoteAddr() + "]<br>");
		out.println("Req Remote Host :[" + req.getRemoteHost() + "]<br>");
		out.println("Req Remote Port:[" + req.getRemotePort() + "]<br>");
		out.println("Req Remote User:[" + req.getRemoteUser() + "]<br>");
		out.println("Req Requested Session Id:[" + req.getRequestedSessionId()
				+ "]<br>");
		out.println("Req Request URI:[" + req.getRequestURI() + "]<br>");
		out.println("Req Scheme:[" + req.getScheme() + "] isSecure="
				+ ServletUtil.isSecure(req) + "<br>");
		out.println("Req Server Name:[" + req.getServerName() + "]<br>");
		out.println("Req Server Port:[" + req.getServerPort() + "]<br>");
		out.println("Req Servlet Path:[" + req.getServletPath() + "]<br>");
		out.println("Req Request URL:[" + req.getRequestURL() + "]<br>");

		SimpleDateFormat fmt = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss.SSSSSS");
		fmt.setTimeZone(new SimpleTimeZone(0, ""));
		out.println("<hr noshade><p>Server Time:[" + fmt.format(new Date())
				+ "]<br>Locale:[" + Locale.getDefault() + "]</p>");

		// Test logging
		log.finest("finest level");
		log.finer("finer level");
		log.fine("fine level");
		log.config("config level");
		log.info("info level");
		log.warning("warning level");
		log.severe("severe level");
		System.out.println("stdout level");
		System.err.println("stderr level");

		// Test URL Fetch
		try {
			URL url = new URL("http://ae-book.appspot.com/blog/atom.xml/");

			FetchOptions options = FetchOptions.Builder.doNotFollowRedirects()
					.disallowTruncate();
			HTTPRequest request = new HTTPRequest(url, HTTPMethod.GET, options);

			URLFetchService service = URLFetchServiceFactory
					.getURLFetchService();
			HTTPResponse response = service.fetch(request);

			byte[] content = response.getContent();
			out.println("<p>Read PGAE blog feed (" + content.length
					+ " characters).</p><pre>" // + new String(content)
					+ "</pre>");

		} catch (ResponseTooLargeException e1) {
			out.println("<p>ResponseTooLargeException: " + e1 + "</p>");

		} catch (MalformedURLException e1) {
			out.println("<p>MalformedURLException: " + e1 + "</p>");

		} catch (IOException e1) {
			out.println("<p>IOException: " + e1 + "</p>");
		}

		// CREATE SEND MAIL URL

		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		String appEmailAddress = "support@" + appId + ".appspotmail.com";
		out.println("<p>WP Email: [" + "<a href=\"mailto:"
				+ appEmailAddress + "\">" + appEmailAddress + "</a>]</p>");
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
			String email = "/_ah/admin/inboundmail";
			out.println("<p>Dev email console: " + "<a href=\"" + email + "\">"
					+ email + "</a> </p>");

		} 
		// SEND TEXT MESSAGE
		// http://code.google.com/appengine/docs/java/xmpp/overview.html#Sending_Instant_Messages
		// anything@version.latest.app-id.appspotchat.com
		// wordpong@appspot.com
		// anything@wordpong.appspotchat.com
		String id = "testtext@wordpong.appspotchat.com";
		JID jid = new JID(id);
		String msgBody = "Test msg from PrintEnvironmentServlet";
		Message msg = new MessageBuilder().withRecipientJids(jid).withBody(
				msgBody).build();
		boolean messageSent = false;
		XMPPService xmpp = XMPPServiceFactory.getXMPPService();
		if (xmpp.getPresence(jid).isAvailable()) {
			SendResponse status = xmpp.sendMessage(msg);
			messageSent = (status.getStatusMap().get(jid) == SendResponse.Status.SUCCESS);
			out.println("XMPP Send to: [" + id + "] Result:" + messageSent+"</p>");
		} else {
			out.println("XMPP not available</p>");
		}
	}

	/*
	 * public static void printDirectoryListing(PrintWriter out, File dir,
	 * String indent) { if (dir.isDirectory()) { out.println(indent +
	 * escapeHtmlChars(dir.getName()) + "/"); String[] contents = dir.list();
	 * for (int i = 0; i < contents.length; i++) { printDirectoryListing(out,
	 * new File(dir, contents[i]), indent + "  "); } } else { out.println(indent
	 * + escapeHtmlChars(dir.getName())); } }
	 */

}
