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

// Once a user logs in for the first time,
// TODO: Any FriendInvites are moved to their User.<GameMyTurn> list as a FriendRequest

@Model(schemaVersion = 1)
public class FriendRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    @Attribute(listener = CreationDate.class)
    private Date createdAt;

    @Attribute(unindexed = true)
    private Key inviterKey;

    @Attribute(unindexed = true)
    private Date invitedAt; // copied from FriendInvite

    public FriendRequest() {
    }

    public FriendRequest(FriendInvite fi) {
        invitedAt = fi.getCreatedAt();
        inviterKey = fi.getInviterKey();
    }

    public String getCreatedAtString() {
        return TimeUtil.getElapsedTimeString(createdAt);
    }

    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     * 
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     * 
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     * 
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    public Key getInviterKey() {
        return inviterKey;
    }

    public void setInviterKey(Key inviterKey) {
        this.inviterKey = inviterKey;
    }

    public Date getInvitedAt() {
        return invitedAt;
    }

    public void setInvitedAt(Date invitedAt) {
        this.invitedAt = invitedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        FriendRequest other = (FriendRequest) obj;

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
