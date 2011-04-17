package com.wordpong.api.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;
import com.wordpong.cmn.util.TimeUtil;

// Sent to a new user of wordpong
// converted to FriendRequest once they login

@Model(schemaVersion = 1)
public class FriendInvite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    @Attribute(listener = CreationDate.class)
    private Date createdAt;

    @Attribute(unindexed = false)
    private String inviteeEmail;

    @Attribute(unindexed = false)
    private Key inviterKey;

    @Attribute(unindexed = true)
    private String inviterFirstName;

    @Attribute(unindexed = true)
    private String inviterLastName;

    @Attribute(unindexed = true)
    private String inviterEmail;

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAtString() {
        return TimeUtil.getElapsedTimeString(createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getInviteeEmail() {
        return inviteeEmail;
    }

    public void setInviteeEmail(String inviteeEmail) {
        this.inviteeEmail = inviteeEmail;
    }

    public Key getInviterKey() {
        return inviterKey;
    }

    public void setInviterKey(Key inviterKey) {
        this.inviterKey = inviterKey;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getInviterFirstName() {
        return inviterFirstName;
    }

    public void setInviterFirstName(String inviterFirstName) {
        this.inviterFirstName = inviterFirstName;
    }

    public String getInviterLastName() {
        return inviterLastName;
    }

    public void setInviterLastName(String inviterLastName) {
        this.inviterLastName = inviterLastName;
    }

    public String getInviterEmail() {
        return inviterEmail;
    }

    public void setInviterEmail(String inviterEmail) {
        this.inviterEmail = inviterEmail;
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
        FriendInvite other = (FriendInvite) obj;
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
