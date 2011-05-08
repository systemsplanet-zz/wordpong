package com.wordpong.api.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class QuestionView {

    private String questionKeyString;
    private String questionInfo;

    public String getQuestionKeyString() {
        return questionKeyString;
    }

    public void setQuestionKeyString(String ks) {
        this.questionKeyString = ks;
    }

    public String getQuestionInfo() {
        return questionInfo;
    }

    public void setQuestionInfo(String questionInfo) {
        this.questionInfo = questionInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
