package com.wordpong.api.svc.dao;

import com.wordpong.api.model.Question;
import com.wordpong.api.svc.dao.err.DaoException;

public interface DaoQuestion {
    Question save(Question u) throws DaoException;

    

}
