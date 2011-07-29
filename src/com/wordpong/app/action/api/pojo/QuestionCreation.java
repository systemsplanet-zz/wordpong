package com.wordpong.app.action.api.pojo;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.wordpong.api.model.Question;
import com.wordpong.api.pojo.LocaleDisplay;

public class QuestionCreation {

    List<String> questions;
    String title;
    String description;
    int visibility=Question.VISIBILITY_PRIVATE;
    int intimacy=Question.INTIMACY_CLICHES;
    String locale=LocaleDisplay.LOCALE_EN_US;

    public QuestionCreation() {
        super();
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

    public int getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(int intimacy) {
        this.intimacy = intimacy;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
