package com.wordpong.api.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Model(schemaVersion = 1)
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private Key userKey;

    private Key questionKey;

    @Attribute(unindexed = true)
    private String questionDescription; // copied from question.description

    private List<String> answers;

    @Attribute(unindexed = true)
    private String localeString;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getKeyString() {
        String k = KeyFactory.keyToString(key);
        return k;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getLocaleString() {
        return localeString;
    }

    public void setLocaleString(String locale) {
        this.localeString = locale;
    }

    public Key getUserKey() {
        return userKey;
    }

    public void setUserKey(Key userKey) {
        this.userKey = userKey;
    }

    public String getQuestionKeyString() {
        String k = KeyFactory.keyToString(questionKey);
        return k;
    }
    
    public Key getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(Key questionsKey) {
        this.questionKey = questionsKey;
    }

    public void setQuestionKeyString(String questionsKeyString) {
        if (questionsKeyString != null) {
            questionKey = KeyFactory.stringToKey(questionsKeyString);
        }
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
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
        Answer other = (Answer) obj;
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
