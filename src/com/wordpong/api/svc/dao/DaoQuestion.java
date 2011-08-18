package com.wordpong.api.svc.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.err.DaoException;

public interface DaoQuestion {
    Question createQuestion(Question q) throws DaoException;

    Key updateQuestion(Question q) throws DaoException;

    List<Question> get(List<Key> keys) throws DaoException;

    List<Question> getByKeyStrings(List<String> keys) throws DaoException;

    Question getQuestion(String questionKeyStr) throws DaoException;

    List<Question> getQuestions(User u) throws DaoException;

    List<Question> getMyQuestions(User u) throws DaoException;

}
