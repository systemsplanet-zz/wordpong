package com.wordpong.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.sourceforge.stripes.util.CryptoUtil;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.wordpong.util.ImageUtil;

@Model(schemaVersion = 1)
public class User implements Serializable {
    public static final String DEFAULT_TIMEZONE = "EST";

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

    // map friend keyString to the total points with that friend
    @Attribute(unindexed = true, lob = true)
    private Map<String, Integer> friendPointsMap = new HashMap<String, Integer>();

    @Attribute(unindexed = true)
    private Set<Key> gameKeys = new HashSet<Key>();

    // points not saved. Just used for display purposes
    @Attribute(persistent = false)
    private int points;

    @Attribute(listener = ModificationDate.class)
    Date updatedAt;

    @Attribute(listener = CreationDate.class)
    Date createdAt;

    /**
     * The preferred locale (nullable)
     */
    @Attribute(unindexed = true)
    private String localeString = Locale.US.toString();

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

    public String getKeyString() {
        String k = KeyFactory.keyToString(key);
        return k;
    }

    public String getKeyStringEncrypted() {
        String ks = KeyFactory.keyToString(key);
        String keyEncrypted = CryptoUtil.encrypt(ks);
        return keyEncrypted;
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

    public void setEmail(String e) {
        if (e != null) {
            e = e.trim().toLowerCase();
        }
        this.email = e;
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

    public String getDetails() {
        return firstName + " " + lastName + " (" + email + ")";
    }

    public Set<Key> getFriends() {
        return friends;
    }

    public void setFriends(Set<Key> friends) {
        this.friends = friends;
    }

    public String getPictureUrl() {
        if (pictureUrl == null) {
            pictureUrl = ImageUtil.getPictureUrl(email);
        }
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setLocale(Locale locale) {
        if (locale != null)
            localeString = locale.toString();
    }

    public Locale getLocale() {
        Locale result;
        if (localeString != null && localeString.indexOf("_") != -1) {
            String[] ls = localeString.split("_");
            result = new Locale(ls[0], ls[1]);
        } else {
            result = Locale.US;
        }
        return result;
    }

    public String getLocaleString() {
        if (localeString == null) {
            localeString = Locale.US.toString();
        }
        return localeString;
    }

    public void setLocaleString(String localeString) {
        this.localeString = localeString;
    }

    public void addFriendPoints(String keyString, int points) {
        int pts = getFriendPoints(keyString);
        pts += points;
        setFriendPoints(keyString, pts);
    }

    public void setFriendPoints(String keyString, int pts) {
        friendPointsMap.put(keyString, pts);
    }

    public int getFriendPoints(String keyString) {
        int result = 0;
        if (friendPointsMap != null && friendPointsMap.containsKey(keyString)) {
            result = friendPointsMap.get(keyString);
        }
        return result;
    }

    public Map<String, Integer> getFriendPointsMap() {
        return friendPointsMap;
    }

    public void setFriendPointsMap(Map<String, Integer> friendPointsMap) {
        this.friendPointsMap = friendPointsMap;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    /*
     * public int getTotalPoints() { int total = 0; if (games != null &&
     * games.size() > 0) { for (int i = 0; i < games.size(); i++) { total +=
     * games.get(i).getPoints(); } } return total; }
     */
    public void addGame(Game g) {
        if (g != null) {
            Key key = g.getKey();
            if (gameKeys != null) {
                gameKeys.add(key);
            }
        }
    }

    public void removeGame(Game g) {
        if (g != null) {
            Key key = g.getKey();
            if (key != null && gameKeys != null && gameKeys.contains(key)) {
                gameKeys.remove(key);
            }
        }
    }

    public Set<Key> getGameKeys() {
        return gameKeys;
    }

    public void setGameKeys(Set<Key> gameKeys) {
        this.gameKeys = gameKeys;
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
