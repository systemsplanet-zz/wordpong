package com.wordpong.api.svc.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.wordpong.api.meta.QuestionMeta;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.err.DaoException;

public class DaoQuestionImpl extends DaoBase<Question> implements DaoQuestion {
    private static final Logger log = Logger.getLogger(DaoQuestionImpl.class.getName());

    // Create a Question with a unique email address inside a transaction
    // since queries are not part of a transaction there is a chance of creating
    // duplicates!
    public Question createQuestion(Question q) throws DaoException {
        Transaction txn = Datastore.beginTransaction();
        Question existing = findByTitle(q.getTitle());
        if (existing == null) {
            q = create(q);
            txn.commit();
        } else {
            txn.rollback();
            throw new DaoException("Duplicate Question");
        }
        return q;
    }

    private Question create(Question q) throws DaoException {
        if (q == null) {
            throw new DaoException("cant save null Question");
        }
        try {
            String action = q.getKey() == null ? "Created" : "Updated";
            Key key = put(q);
            log.info(action + " Question:" + q + " key:" + key);
        } catch (Exception e) {
            log.warning("unable to save Question:" + q);
            throw new DaoException(e.getMessage());
        }
        return q;
    }

    // return null if not found or exception if more than one found
    public Question findByTitle(String title) throws DaoException {
        Question result = null;
        QuestionMeta e = QuestionMeta.get();
        try {
            result = Datastore.query(e).filter(e.title.equal(title)).asSingle();
        } catch (Exception ex) {
            // should never happen!
            // com.google.appengine.api.datastore.PreparedQuery$TooManyResultsException
            throw new DaoException("findByTitle title:[" + title + "] err:" + ex.getMessage());
        }
        return result;
    }

    @Override
    public Question getQuestion(String questionKeyStr) throws DaoException {
        Key k = KeyFactory.stringToKey(questionKeyStr);
        Question result = get(k);
        return result;
    }

    @Override
    // dont allow the title to be changed!! It's the unique Key
    public Key updateQuestion(Question u) throws DaoException {
        Key k = put(u);
        return k;
    }
 
    // Get the list of private/public questions for a user
    public List<Question> getMyQuestions(User u) throws DaoException {
        List<Question> result;
        QuestionMeta e = QuestionMeta.get();
        try {
            Key uKey = u.getKey();
            result = Datastore.query(e).filter(e.user.equal(uKey)).asList();
        } catch (Exception ex) {
            throw new DaoException("Err:" + ex.getMessage());
        }
        Collections.sort(result, Question.TITLE_ORDER);
        return result;
    }

    private List<Question> getPublicQuestions() throws DaoException {
        List<Question> result = null;
        QuestionMeta e = QuestionMeta.get();
        try {
            result = Datastore.query(e).filter(e.visibility.equal(Question.VISIBILITY_PUBLIC)).asList();
        } catch (Exception ex) {
            // should never happen!
            // com.google.appengine.api.datastore.PreparedQuery$TooManyResultsException
            throw new DaoException("Err:" + ex.getMessage());
        }
        Collections.sort(result, Question.TITLE_ORDER);
        return result;
    }

    // get unique, sorted list of public questions plus my public/private questions
    public List<Question> getQuestions(User u) throws DaoException {
        List<Question> result = new ArrayList<Question>();
        Map<String, Question> m = new HashMap<String, Question>();
        List<Question> mq = getMyQuestions(u);
        for (Question q : mq) {
            m.put(q.getTitle(), q);
        }
        List<Question> pq = getPublicQuestions();
        for (Question q : pq) {
            m.put(q.getTitle(), q);
        }
        for (Map.Entry<String, Question> entry : m.entrySet()) {
            result.add((Question) entry.getValue());
        }
        Collections.sort(result, Question.TITLE_ORDER);
        return result;
    }
}
