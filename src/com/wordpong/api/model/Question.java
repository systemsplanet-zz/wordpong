package com.wordpong.api.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sourceforge.stripes.util.CryptoUtil;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.wordpong.app.action.apiv1.pojo.QuestionCreation;

@Model(schemaVersion = 1)
public class Question implements Serializable {

    public static final Comparator<Question> TITLE_ORDER = new Comparator<Question>() {
        public int compare(Question e1, Question e2) {
            return e1.getTitle().toLowerCase().compareTo(e2.getTitle().toLowerCase());
        }
    };
    private static final long serialVersionUID = 1L;

    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_PRIVATE = 0;

    public static final int INTIMACY_CLICHES = 0;
    public static final int INTIMACY_FACTS = 1;
    public static final int INTIMACY_OPINIONS = 2;
    public static final int INTIMACY_HOPES = 3;
    public static final int INTIMACY_FEELINGS = 4;
    public static final int INTIMACY_FEARS = 5;
    public static final int INTIMACY_NEEDS = 6;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    @Attribute(listener = ModificationDate.class)
    Date updatedAt;

    @Attribute(listener = CreationDate.class)
    Date createdAt;

    @Attribute(unindexed = false)
    private Key user;

    @Attribute(unindexed = true)
    private List<String> questions;

    @Attribute(unindexed = false)
    private String title;

    @Attribute(unindexed = true)
    private String description;

    @Attribute(unindexed = true)
    Set<String> tags = new TreeSet<String>();

    // eg pt_BR, en_US
    @Attribute(unindexed = true)
    private String localeString;

    // 0 is private
    // 1 is public
    @Attribute(unindexed = false)
    private int visibility;

    // 0 is cliches
    // 1 is facts
    // 2 is opinions
    // 3 is hopes and dreams
    // 4 is feelings
    // 5 is fears/failures/weaknesses
    // 6 is needs
    @Attribute(unindexed = false)
    private int intimacyLevel;

    // GETTER/SETTERS

    public Key getUser() {
        return user;
    }

    public void setUser(User u) {
        this.user = u.getKey();
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

    public String getLocaleString() {
        return localeString;
    }

    public void setLocaleString(String locale) {
        this.localeString = locale;
    }

    public String getKeyString() {
        String k = KeyFactory.keyToString(key);
        return k;
    }

    public String getKeyStringEncrypted() {
        String ks = getKeyString();
        String keyEncrypted = CryptoUtil.encrypt(ks);
        return keyEncrypted;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public void setKeyString(String questionsKeyString) {
        if (questionsKeyString != null) {
            key = KeyFactory.stringToKey(questionsKeyString);
        }
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Set<String> getTags() {
        if (tags == null) {
            tags = new TreeSet<String>();
        }
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
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

    public Question() {
        super();
    }

    public Question(QuestionCreation qc) {
        setTitle(qc.getTitle());
        setDescription(qc.getDescription());
        setVisibility(qc.getVisibility());
        setIntimacyLevel(qc.getIntimacy());
        setLocaleString(qc.getLocale());
        setQuestions(qc.getQuestions());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
