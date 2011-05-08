package com.wordpong.api.svc.dao;

import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;

import com.google.appengine.api.datastore.Key;
import com.wordpong.api.model.Answer;
import com.wordpong.api.svc.dao.err.DaoException;
 
public class DaoAnswerImpl extends DaoBase<Answer> implements DaoAnswer {
    private static final Logger log = Logger.getLogger(DaoAnswerImpl.class.getName());

    public Answer save(Answer q) throws DaoException {
        if (q == null) {
            throw new DaoException("cant save null Answer");
        }
        try {
            String action = q.getKey() == null ? "Created" : "Updated";
            // Key key = Datastore.allocateId(Answer.class);
            // u.setKey(key);
            Key key = put(q);
            log.info(action + " Answer:" + q + " key:" + key);
        } catch (Exception e) {
            log.warning("unable to save Answer:" + q);
            throw new DaoException(e.getMessage());
        }
        return q;
    }
}
