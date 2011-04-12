package com.wordpong.api.svc.dao;

import com.wordpong.api.model.Question;

public interface DaoQuestion {
    Question save(Question u) throws DaoException;

    

}
