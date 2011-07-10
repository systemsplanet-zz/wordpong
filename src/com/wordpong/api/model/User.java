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

import net.sourceforge.stripes.util.CryptoUtil;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.wordpong.api.pojo.Role;

@Model(schemaVersion = 1)
public class User implements Serializable {
	public static final String IMAGE_DEFAULT = "https://wordpong.appspot.com/i/p/u.png";
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

	// Points to User (new Friend), Game (my move in a game), or Invite (new
	// invitation to join)
	// TODO: test a set of mixed key types
	@Attribute(unindexed = true)
	private List<Key> invitationRequests = new ArrayList<Key>();

	// Points to InviteFriend (invitations sent to invite friends), Game
	// (invites to play),
	@Attribute(unindexed = true)
	private Set<Key> theirTurns = new HashSet<Key>();

	@Attribute(persistent = false)
	private List<Role> roles = new ArrayList<Role>();

	@Attribute(persistent = false)
	private List<Game> games = new ArrayList<Game>();

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
	 * The preferred time zone (not nullable)
	 */
	@Attribute(unindexed = true)
	private String timeZoneString;

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

	public String getDetails() {
		return firstName + " " + lastName + " (" + email + ")";
	}

	public Set<Key> getFriends() {
		return friends;
	}

	public void setFriends(Set<Key> friends) {
		this.friends = friends;
	}

	public List<Key> getInvitationRequests() {
		return invitationRequests;
	}

	public void setInvitationRequests(List<Key> invitationRequests) {
		this.invitationRequests = invitationRequests;
	}

	public Set<Key> getTheirTurns() {
		return theirTurns;
	}

	public void setTheirTurns(Set<Key> theirTurns) {
		this.theirTurns = theirTurns;
	}

	public String getPictureUrl() {
		if (email != null) {
			String e = email.toLowerCase().trim();
			try {
				MessageDigest m = MessageDigest.getInstance("MD5");
				m.update(e.getBytes(), 0, e.length());
				String hash = new BigInteger(1, m.digest()).toString(16);
				pictureUrl = "http://www.gravatar.com/avatar/" + hash + "?d="
						+ IMAGE_DEFAULT;
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

	public void setTimeZoneString(String timeZone) {
		if (timeZone == null) {
			timeZone = DEFAULT_TIMEZONE;
		}
		timeZoneString = timeZone;
	}

	public String getTimeZoneString() {
		if (timeZoneString == null) {
			timeZoneString = DEFAULT_TIMEZONE;
		}
		return timeZoneString;
	}

	public void setTimeZone(TimeZone timeZone) {
		if (timeZone != null) {
			timeZoneString = timeZone.getID();
		}
		if (timeZoneString == null) {
			timeZoneString = DEFAULT_TIMEZONE;
		}
	}

	public TimeZone getTimeZone() {
		if (timeZoneString == null) {
			timeZoneString = DEFAULT_TIMEZONE;
		}
		return TimeZone.getTimeZone(timeZoneString);
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public int getTotalPoints() {
		int total = 0;
		if (games != null && games.size() > 0) {
			for (int i = 0; i < games.size(); i++) {
				total += games.get(i).getPoints();
			}
		}
		return total;
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
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
