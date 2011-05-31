package com.wordpong.api.model;

import java.io.Serializable;

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

	// points to a Question object
	private Key questionsKey;

	// points to an Answer object and the user who gave the answers
	private Key answersKey;

	// User playing this game, ie matching questionsKey to answersKey
	private Key userKey;

	private boolean isCompleted = false;

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

	public Key getUserKey() {
		return userKey;
	}

	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}

	public String getUserKeyString() {
		String k = KeyFactory.keyToString(userKey);
		return k;
	}

	public void setUserKeyString(String kStr) {
		Key k = KeyFactory.stringToKey(kStr);
		userKey = k;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Key getQuestionsKey() {
		return questionsKey;
	}

	public void setQuestionsKey(Key questionsKey) {
		this.questionsKey = questionsKey;
	}

	public String getQuestionsKeyString() {
		String k = KeyFactory.keyToString(questionsKey);
		return k;
	}

	public void setQuestionsKeyString(String kStr) {
		Key k = KeyFactory.stringToKey(kStr);
		questionsKey = k;
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

	public void setAnswersKeyString(String kStr) {
		Key k = KeyFactory.stringToKey(kStr);
		answersKey = k;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
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
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
