package com.wordpong.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.appengine.api.datastore.Key;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

@Model(schemaVersion = 1)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    private Key user;
    
    private List<String> questions;
    
    private String title;
    
    private String description;
    
    //0 is private
    //1 is public
    private int visibility;
    
    
    // 0 is cliches
    // 1 is facts
    // 2 is opinions
    // 3 is hopes and dreams
    // 4 is feelings
    // 5 is fears/failures/weaknesses
    // 6 is needs
    private int intimacyLevel;

    @Attribute(listener = ModificationDate.class)
    Date updatedAt;
    
    public Key getUser() {
		return user;
	}

	public void setUser(Key user) {
		this.user = user;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public int getIntimacyLevel() {
		return intimacyLevel;
	}

	public void setIntimacyLevel(int intimacyLevel) {
		this.intimacyLevel = intimacyLevel;
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

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Attribute(listener = CreationDate.class)
    Date createdAt;
    
    /**
     * The preferred locale (nullable)
     */
    @Attribute(unindexed = true, lob = true)
    private Locale locale;

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
        Question other = (Question) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }
}
