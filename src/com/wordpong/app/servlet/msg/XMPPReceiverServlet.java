package com.wordpong.app.servlet.msg;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.MessageType;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

// http://code.google.com/appengine/docs/java/xmpp/overview.html#Sending_Instant_Messages
// anything@version.latest.app-id.appspotchat.com
// wordpong@appspot.com
// anything@wordpong.appspotchat.com
// testtext@wordpong.appspotchat.com
// BUG: "The use of Google Apps domains in XMPP addresses is not yet supported for apps"
//    http://code.google.com/appengine/docs/java/xmpp/overview.html
//    http://code.google.com/p/googleappengine/issues/detail?id=2545
@SuppressWarnings("serial")
public class XMPPReceiverServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(XMPPReceiverServlet.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		log.info("Text msg post");
		try {
			XMPPService xmpp = XMPPServiceFactory.getXMPPService();
			Message message = xmpp.parseMessage(req);

			JID fromJid = message.getFromJid();
			String body = message.getBody();
			log.info("Text msg from: " + fromJid.toString() + " body:" + body);

			// send reply
			Message reply = new MessageBuilder().withRecipientJids(
					message.getFromJid()).withMessageType(MessageType.NORMAL)
					.withBody("got your message:[" + body + "]").build();
			xmpp.sendMessage(reply);
		} catch (Exception e) {
			log.warning("error processing text msg:" + e.getLocalizedMessage());
		}
	}
}
