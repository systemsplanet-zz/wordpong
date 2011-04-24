package com.wordpong.api.svc.dao;

import java.util.List;
import java.util.Set;

import com.wordpong.api.model.Question;
import com.wordpong.api.svc.dao.err.DaoException;

public interface DaoTagQuestion {

    // persist the question and update or create tag models
    void setTags(Question q, Set<String> newTags) throws DaoException;

    // get the questions that are tagged with tags
    List<Question> getQuestions(Set<String> tags) throws DaoException;
}
