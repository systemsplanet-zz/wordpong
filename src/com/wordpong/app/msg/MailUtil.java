package com.wordpong.app.msg;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.wordpong.api.model.EmailMessage;

public class MailUtil {
    private static final Logger log = Logger.getLogger(MailUtil.class.getName());

    public static void sendAdminMail(EmailMessage m) throws Exception {
        m.setFromEmail("wordpong@systemsplanet.com");
        m.setFromName("WordPong Admin");
        sendMail(m);
    }

    public static void sendMail(EmailMessage m) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(m.getFromEmail(), m.getFromName()));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m.getToEmail(), m.getToName()));
            msg.setSubject(m.getSubject());
            msg.setText(m.getBody());
            Transport.send(msg);
            log.info("send email:" + m);
        } catch (Exception e) {
            log.severe("send email:" + m + " err:" + e.toString());
            throw new Exception(e.getMessage());
        }
    }
}