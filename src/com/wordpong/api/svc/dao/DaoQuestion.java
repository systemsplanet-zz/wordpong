package com.wordpong.api.svc.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;

public interface DaoQuestion {
    Question save(Question u) throws DaoException;

    List<Question> get(List<Key> keys);
    
    void seedQuestions(User user)throws DaoException;

}
