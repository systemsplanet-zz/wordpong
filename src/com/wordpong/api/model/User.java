package com.wordpong.api.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.crypto.SecretKey;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.wordpong.api.pojo.Role;

@Model(schemaVersion = 1)
public class User implements Serializable {
    public static final String IMAGE_DEFAULT = "https://wordpong.appspot.com/i/p/u.png";

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    @Attribute(unindexed = false)
    private String email;

    @Attribute(unindexed = true)
    private String firstName;

    @Attribute(unindexed = true)
    private String lastName;

    @Attribute(unindexed = true)
    private boolean activated;

    @Attribute(unindexed = true)
    private String password; // encrypted by PasswordTypeConverter

    @Attribute(unindexed = true)
    private String pictureUrl;

    @Attribute(unindexed = true)
    private Set<Key> friends = new HashSet<Key>();

    // Points to User (new Friend), Game (my move in a game), or Invite (new invitation to join)
    @Attribute(unindexed = true)    
    private Set<Key> myTurns = new HashSet<Key>();

    @Attribute(persistent = false)
    private List<Role> roles = new ArrayList<Role>();

    @Attribute(listener = ModificationDate.class)
    Date updatedAt;

    @Attribute(listener = CreationDate.class)
    Date createdAt;

    /**
     * The preferred locale (nullable)
     */
    @Attribute(unindexed = true, lob = true)
    private Locale locale;

    /**
     * The preferred time zone (not nullable)
     */
    @Attribute(unindexed = true, lob = true)
    private TimeZone timeZone;

    public List<Role> getRoles() {
        return roles;
    }

    public boolean hasRole(String role) {
        boolean result = Role.hasRole(roles, role);
        return result;
    }

    /**
     * The secret key of the account, used to decrypt the information in the
     * account
     */
    @Attribute(persistent = false)
    private SecretKey encryptionKey = null;

    /**
     * Returns the key.
     * 
     * @return the key
     */
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SecretKey getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(SecretKey encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Set<Key> getFriends() {
        return friends;
    }

    public void setFriends(Set<Key> friends) {
        this.friends = friends;
    }

    public Set<Key> getMyTurns() {
        return myTurns;
    }

    public void setMyTurns(Set<Key> myTurns) {
        this.myTurns = myTurns;
    }

    public String getPictureUrl() {
        if (email != null) {
            String e = email.toLowerCase().trim();
            try {
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.update(e.getBytes(), 0, e.length());
                String hash = new BigInteger(1, m.digest()).toString(16);
                pictureUrl = "http://www.gravatar.com/avatar/" + hash + "?d=" + IMAGE_DEFAULT;
            } catch (NoSuchAlgorithmException e1) {
            }
        }
        if (pictureUrl == null) {
            pictureUrl = IMAGE_DEFAULT;
        }
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public Locale getLocale() {
        return locale;
    }

    public TimeZone getTimeZone() {
        return timeZone;
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
        User other = (User) obj;
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
