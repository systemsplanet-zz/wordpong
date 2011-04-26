package com.wordpong.api.svc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.google.common.base.Predicate;
import com.wordpong.api.meta.QuestionMeta;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.User;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public class DaoQuestionImpl extends DaoBase<Question> implements DaoQuestion {
    private static final Logger log = Logger.getLogger(DaoQuestionImpl.class.getName());

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

    @Override
    public void seedQuestions(final User u) throws DaoException {
        final String msg = "seedQuestions: user:" + u;
        try {
            Predicate<Atomic> WORK = new Predicate<Atomic>() {
                public boolean apply(Atomic at) {
                    boolean result = false;
                    try {
                        List<String> qs = new ArrayList<String>();
                        Set<String> tags = new TreeSet<String>();
                        Question q = null;
                        q = new Question();
                        q.setDescription("intimacy level 0");
                        q.setIntimacyLevel(0);
                        q.setLocale("en_US");
                        qs.clear();
                        qs.add("Eye Color?");
                        qs.add("Hair Color?");
                        qs.add("Car Color?");
                        qs.add("Home Color?");
                        q.setQuestions(qs);
                        tags.clear();
                        tags.add("favorites");
                        tags.add("personal");
                        tags.add("colors");
                        q.setTags(tags);
                        q.setTitle("Favorite Colors");
                        q.setUser(u.getKey());
                        q.setVisibility(0);
                        at.put(q);
                        q.setKey(null);
                        q.setDescription("intimacy level 1");
                        q.setIntimacyLevel(1);
                        qs.clear();
                        qs.add("Birth Date?");
                        qs.add("Hire Date?");
                        qs.add("Marriage Date?");
                        qs.add("Garduation Date?");
                        q.setQuestions(qs);
                        tags.clear();
                        tags.add("favorites");
                        tags.add("personal");
                        tags.add("dates");
                        q.setTags(tags);
                        q.setTitle("Favorite Dates");
                        q.setVisibility(1);
                        at.put( q);
                        result =true;
                    } catch (Exception e) {
                        log.warning("err:"+ e.getMessage());
                    }
                    return result;
                }

                public String toString() {
                    return msg;
                }
            };
            Atomic.transact(WORK);
        } catch (Exception e1) {
            String m = msg + "seedQuestions failed. Err:" + e1.getMessage();
            log.warning(m);
            throw new DaoException(m);
        }
    }
}