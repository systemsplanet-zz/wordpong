package com.wordpong.api.svc.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.common.base.Predicate;

import com.wordpong.api.meta.TagQuestionMeta;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.TagQuestion;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public class DaoTagQuestionImpl extends DaoBase<TagQuestion> implements DaoTagQuestion {
    private static final Logger log = Logger.getLogger(DaoTagQuestionImpl.class.getName());

    @Override
    public void setTags(final Question q, final Set<String> newTags) throws DaoException {
        final String msg = "setTags: question:" + q;
        final TagQuestionMeta e = TagQuestionMeta.get();
        try {
            Predicate<Atomic> WORK = new Predicate<Atomic>() {
                @SuppressWarnings("unchecked")
                public boolean apply(Atomic at) {
                    boolean result = false;
                    try {
                        Set<String> oldTags = q.getTags();
                        q.setTags(newTags);
                        // Update question
                        at.put(Question.class, q);
                        HashSet<String> tagsToAdd = (HashSet<String>) ((HashSet<String>) newTags).clone();
                        tagsToAdd.removeAll(oldTags);
                        
                        // add or update new tags
                        for (String tag : tagsToAdd) {
                            // find if existing
                            TagQuestion tq = Datastore.query(e).filter(e.tag.equal(tag)).asSingle();
                            if (tq != null && tq.getKey() != null) {
                                // update existing tag
                                List<Key> keys = tq.getKeys();
                                keys.add(q.getKey());
                            } else {
                                // add new tag
                                tq = new TagQuestion();
                                tq.setTag(tag);
                                List<Key> keys = new ArrayList<Key>();
                                keys.add(q.getKey());
                                tq.setKeys(keys);
                            }
                            at.put(TagQuestion.class, tq);
                        }
                        
                        // remove old tags
                        Set<String> tagsToRemove = oldTags;
                        tagsToRemove.removeAll(newTags);
                        for (String tag : tagsToRemove) {
                            TagQuestion tq = Datastore.query(e).filter(e.tag.equal(tag)).asSingle();
                            if (tq != null && tq.getKey() != null) {
                                at.delete(tq.getKey());
                            }
                        }
                        result = true;
                    } catch (Exception e) {
                    }
                    return result;
                }

                public String toString() {
                    return msg;
                }
            };
            Atomic.transact(WORK);
        } catch (Exception e1) {
            String m = msg + " failed. Err:" + e1.getMessage();
            log.warning(m);
            throw new DaoException(m);
        }
    }

    @Override
    public List<Question> getQuestions(Set<String> tags) throws DaoException {
        final TagQuestionMeta e = TagQuestionMeta.get();
        final DaoQuestion dq = DaoQuestionFactory.getQuestionDao();
        List<Question> result = new ArrayList<Question>();
        for (String tag : tags) {
            TagQuestion tq = Datastore.query(e).filter(e.tag.equal(tag)).asSingle();
            if (tq != null && tq.getKeys() != null) {
                List<Key> keys = tq.getKeys();
                //todo: limit to 1000 max at a time
                List<Question> qs = dq.get(keys); 
                result.addAll(qs);
            }
        }
        return result;
    }
}
