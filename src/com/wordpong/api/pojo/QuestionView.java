package com.wordpong.api.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class QuestionView {

    private String questionKeyString;
    private String questionDescription;

    public String getQuestionKeyString() {
        return questionKeyString;
    }

    public void setQuestionKeyString(String ks) {
        this.questionKeyString = ks;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
