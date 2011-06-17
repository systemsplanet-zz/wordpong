package com.wordpong.api.svc.dao;

import java.util.List;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.wordpong.api.meta.QuestionMeta;
import com.wordpong.api.model.Question;
import com.wordpong.api.svc.dao.err.DaoException;

public class DaoQuestionImpl extends DaoBase<Question> implements DaoQuestion {
	private static final Logger log = Logger.getLogger(DaoQuestionImpl.class
			.getName());

	// Create a Question with a unique email address inside a transaction
	// since queries are not part of a transaction there is a chance of creating
	// duplicates!
	public Question create(Question u) throws DaoException {
		Transaction txn = Datastore.beginTransaction();
		try {
			findByTitle(u.getTitle());
		} catch (DaoException e) {
			u = save(u);
			txn.commit();
		}
		return u;
	}

	public Question save(Question q) throws DaoException {
		if (q == null) {
			throw new DaoException("cant save null Question");
		}
		try {
			String action = q.getKey() == null ? "Created" : "Updated";
			// Key key = Datastore.allocateId(Question.class);
			// u.setKey(key);
			Key key = put(q);
			log.info(action + " Question:" + q + " key:" + key);
		} catch (Exception e) {
			log.warning("unable to save Question:" + q);
			throw new DaoException(e.getMessage());
		}
		return q;
	}

	public Question findByTitle(String email) throws DaoException {
		Question result = null;
		QuestionMeta e = QuestionMeta.get();
		try {
			result = Datastore.query(e).filter(e.title.equal(email)).asSingle();
		} catch (Exception ex) {
			// should never happen!
			// com.google.appengine.api.datastore.PreparedQuery$TooManyResultsException
			throw new DaoException("Err:" + ex.getMessage());
		}
		if (result == null) {
			throw new DaoException("email:" + email);
		}
		return result;
	}

	public List<Question> getPublic() throws DaoException {
		List<Question> result = null;
		QuestionMeta e = QuestionMeta.get();
		try {
			result = Datastore.query(e)
					.filter(e.visibility.equal(Question.VISIBILITY_PUBLIC))
					.asList();
		} catch (Exception ex) {
			// should never happen!
			// com.google.appengine.api.datastore.PreparedQuery$TooManyResultsException
			throw new DaoException("Err:" + ex.getMessage());
		}
		return result;
	}

	@Override
	public Question getQuestion(String questionKeyStr) throws DaoException {
		Key k = KeyFactory.stringToKey(questionKeyStr);
		Question result = get(k);
		return result;
	}
}
