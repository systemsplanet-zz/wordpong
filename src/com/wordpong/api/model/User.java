package com.wordpong.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.SecretKey;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Model(schemaVersion = 1)
public class User implements Serializable {
    public static final String PROP_EMAIL = "email";
    public static final String PROP_FIRST_NAME = "firstName";
    public static final String PROP_LAST_NAME = "lastName";
    public static final String PROP_ACTIVATED = "activated";

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

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

    private Set<Key> friends = new HashSet<Key>();

    
    @Attribute(persistent = false)
    private List<Role> roles = new ArrayList<Role>();

    @Attribute(listener = ModificationDate.class)
    Date updatedAt;

    @Attribute(listener = CreationDate.class)
    Date createdAt;

    public List<Role> getRoles() {
        return roles;
    }

    public boolean hasRole(String role) {
        boolean result = Role.hasRole(roles, role);
        return result;
    }

    @Attribute(persistent = false)
    private Preferences preferences;
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

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
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
