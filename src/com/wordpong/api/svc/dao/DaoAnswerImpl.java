package com.wordpong.api.svc.dao;

import java.util.List;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.wordpong.api.meta.AnswerMeta;
import com.wordpong.api.model.Answer;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.err.DaoException;

public class DaoAnswerImpl extends DaoBase<Answer> implements DaoAnswer {
    private static final Logger log = Logger.getLogger(DaoAnswerImpl.class.getName());

    public Answer save(Answer a) throws DaoException {
        if (a == null) {
            throw new DaoException("cant save null Answer");
        }
        try {
            String action = a.getKey() == null ? "Created" : "Updated";
            Key key = put(a);
            log.info(action + " Answer:" + a + " key:" + key);
        } catch (Exception e) {
            log.warning("unable to save Answer:" + a);
            throw new DaoException(e.getMessage());
        }
        return a;
    }

    public List<Answer> getAnswers(User u) throws DaoException {
        if (u == null) {
            throw new DaoException("cant get answers for null user");
        }
        List<Answer> result = null;
        AnswerMeta e = AnswerMeta.get();
        try {
            Key k = u.getKey();
            result = Datastore.query(e).filter(e.userKey.equal(k)).asList();
        } catch (Exception ex) {
            throw new DaoException("Err:" + ex.getMessage());
        }
        return result;
    }

	@Override
	public Answer getAnswer(String answerKeyStr) throws DaoException {
        Key k = KeyFactory.stringToKey(answerKeyStr);
		Answer result = get(k);
		return result;
	}

}
