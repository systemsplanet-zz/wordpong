
package com.wordpong.app.servlet.msg;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class EmailMessage {
    private String fromEmail;
    private String toEmail;
    private String fromName;
    private String toName;
    private String subject;
    private String body;

    public EmailMessage() {
    }

    public EmailMessage(String subject, String body, String toEmail, String toName, String fromEmail, String fromName) {
        this.subject = subject;
        this.body = body;
        this.toEmail = toEmail;
        this.toName = toName;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
    }

    public EmailMessage(String subject, String body, String toEmail, String toName) {
        this.subject = subject;
        this.body = body;
        this.toEmail = toEmail;
        this.toName = toName;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
