package com.wordpong.api.model;

import java.io.Serializable;

import net.sourceforge.stripes.util.CryptoUtil;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Model(schemaVersion = 1)
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    // points to an Answer object and the user who gave the answers, and the
    // questions key
    private Key answersKey;

    @Attribute(unindexed = true)
    private String questionTitle; // copied from question.Title

    // User who answered questions (copied from Answers)
    private Key inviterUserKey;

    // "FN LN (Email)"
    @Attribute(unindexed = true)
    private String inviterDetails;

    @Attribute(unindexed = true)
    private String inviterPictureUrl;

    // User playing this game, ie matching questionsKey to answersKey
    private Key inviteeUserKey;

    // "FN LN (Email)" of user playing this game
    @Attribute(unindexed = true)
    private String inviteeDetails;

    @Attribute(unindexed = true)
    private String inviteePictureUrl;

    private boolean isCompleted = false;

    // ----------------------------------------------------------------------------
    // GETTERS/SETTERS

    @Attribute(unindexed = true)
    private boolean isIgnored = false;

    public boolean isIgnored() {
        return isIgnored;
    }

    public void setIgnored(boolean isIgnored) {
        this.isIgnored = isIgnored;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getKeyStringEncrypted() {
        String ks = KeyFactory.keyToString(key);
        String keyEncrypted = CryptoUtil.encrypt(ks);
        return keyEncrypted;
    }

    public Key getInviteeUserKey() {
        return inviteeUserKey;
    }

    public void setInviteeUserKey(Key userKey) {
        this.inviteeUserKey = userKey;
    }

    public String getInviteeUserKeyString() {
        String k = KeyFactory.keyToString(inviteeUserKey);
        return k;
    }

    public void setInviteeUserKeytring(String kStr) {
        Key k = KeyFactory.stringToKey(kStr);
        inviteeUserKey = k;
    }

    public Key getInviterUserKey() {
        return inviterUserKey;
    }

    public void setInviterUserKey(Key userKey) {
        this.inviterUserKey = userKey;
    }

    public String getInviterUserKeyString() {
        String k = KeyFactory.keyToString(inviterUserKey);
        return k;
    }

    public void setInviterUserKeyString(String kStr) {
        Key k = KeyFactory.stringToKey(kStr);
        inviterUserKey = k;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Key getAnswersKey() {
        return answersKey;
    }

    public void setAnswersKey(Key answersKey) {
        this.answersKey = answersKey;
    }

    public String getAnswersKeyString() {
        String k = KeyFactory.keyToString(answersKey);
        return k;
    }

    public void setInviteeKeyEncryptedString(String ks) {
        String k = CryptoUtil.decrypt(ks);
        inviteeUserKey = KeyFactory.stringToKey(k);
    }

    public void setInviterKeyEncryptedString(String ks) {
        String k = CryptoUtil.decrypt(ks);
        inviterUserKey = KeyFactory.stringToKey(k);
    }

    public void setAnswersKeyEncryptedString(String kStrEncrypted) {
        String ks = CryptoUtil.decrypt(kStrEncrypted);
        Key k = KeyFactory.stringToKey(ks);
        answersKey = k;
    }

    public void setAnswersKeyString(String ks) {
        Key k = KeyFactory.stringToKey(ks);
        answersKey = k;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getInviterDetails() {
        return inviterDetails;
    }

    public void setInviterDetails(String inviterDetails) {
        this.inviterDetails = inviterDetails;
    }

    public int getPoints() {
        int result = 0;
        if (isCompleted)
            result = 10;
        return result;
    }

    public String getInviteeDetails() {
        return inviteeDetails;
    }

    public void setInviteeDetails(String inviteeDetails) {
        this.inviteeDetails = inviteeDetails;
    }

    public String getInviterPictureUrl() {
        return inviterPictureUrl;
    }

    public void setInviterPictureUrl(String inviterPictureUrl) {
        this.inviterPictureUrl = inviterPictureUrl;
    }

    public String getInviteePictureUrl() {
        return inviteePictureUrl;
    }

    public void setInviteePictureUrl(String inviteePictureUrl) {
        this.inviteePictureUrl = inviteePictureUrl;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Game other = (Game) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
