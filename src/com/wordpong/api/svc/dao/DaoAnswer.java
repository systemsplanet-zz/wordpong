package com.wordpong.api.svc.dao;

import com.wordpong.api.model.Answer;
import com.wordpong.api.svc.dao.err.DaoException;

public interface DaoAnswer {
    Answer save(Answer u) throws DaoException;
}
