package com.wordpong.api.pojo;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class QuestionEdit {

    private String questionKeyString;
 
    private List<String> questions;
 
 
    public List<String> getQuestions() {
        return questions;
    }


    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }


    public String getQuestionKeyString() {
        return questionKeyString;
    }


    public void setQuestionKeyString(String questionKeyString) {
        this.questionKeyString = questionKeyString;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
