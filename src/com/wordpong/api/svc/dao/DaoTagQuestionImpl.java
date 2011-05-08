package com.wordpong.api.svc.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.common.base.Predicate;

import com.wordpong.api.meta.TagQuestionMeta;
import com.wordpong.api.model.Question;
import com.wordpong.api.model.TagQuestion;
import com.wordpong.api.model.User;
import com.wordpong.api.pojo.LocaleDisplay;
import com.wordpong.api.svc.dao.err.DaoException;
import com.wordpong.api.svc.dao.transact.Atomic;

public class DaoTagQuestionImpl extends DaoBase<TagQuestion> implements DaoTagQuestion {
    private static final Logger log = Logger.getLogger(DaoTagQuestionImpl.class.getName());

    @Override
    public void seedQuestions(final User u) throws DaoException {
        try {
            if (u == null)
                throw new DaoException("null user");
            List<String> qs = new ArrayList<String>();
            Set<String> tags = new TreeSet<String>();
            Question q = null;
            q = new Question();
            q.setDescription("private facts - Favorite Colors");
            q.setIntimacyLevel(Question.INTIMACY_CLICHES);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
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
            q.setTitle("Favorite Colors");
            q.setUser(u.getKey());
            q.setVisibility(Question.VISIBILITY_PRIVATE);
            setTags(q, tags);
            
            
            
            q = new Question();
            q.setDescription("public facts - Favorite Dates");
            q.setIntimacyLevel(Question.INTIMACY_FACTS);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
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
            q.setTitle("Favorite Dates");
            q.setUser(u.getKey());
            q.setVisibility(Question.VISIBILITY_PUBLIC);
            setTags(q, tags);


            
            q = new Question();
            q.setDescription("public facts - Favorite Places");
            q.setIntimacyLevel(Question.INTIMACY_FACTS);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
            qs.clear();
            qs.add("Birth Place?");
            qs.add("High School Location?");
            qs.add("Work Place?");
            qs.add("Marriage Place?");
            q.setQuestions(qs);
            tags.clear();
            tags.add("favorites");
            tags.add("personal");
            tags.add("places");
            q.setTitle("Favorite Places");
            q.setUser(u.getKey());
            q.setVisibility(Question.VISIBILITY_PUBLIC);
            setTags(q, tags);

            log.info("seedQuestions: user:" + u);
        } catch (Exception e) {
            log.warning("seedQuestions err:" + e.getMessage());
        }
    }

    @Override
    public void setTags(final Question q, final Set<String> newTags) throws DaoException {
        final String msg = "setTags: question:" + q;
        final TagQuestionMeta e = TagQuestionMeta.get();
        try {
            Predicate<Atomic> WORK = new Predicate<Atomic>() {
                public boolean apply(Atomic at) {
                    boolean result = false;
                    try {
                        Set<String> oldTags = q.getTags();
                        q.setTags(newTags);
                        // Update question
                        at.put(q);

                        Set<String> tagsToAdd = new HashSet<String>();
                        tagsToAdd.addAll(newTags);
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
                            at.put(tq);
                        }

                        // remove old tags
                        Set<String> tagsToRemove = oldTags;
                        tagsToRemove.removeAll(newTags);
                        for (String tag : tagsToRemove) {
                            TagQuestion tq = Datastore.query(e).filter(e.tag.equal(tag)).asSingle();
                            List<Key> keys = tq.getKeys();
                            if (keys != null) {
                                Key k = q.getKey();
                                log.info("remove empty tag:" + tq + " key:" + k);
                                keys.remove(k);
                                if (keys.size() == 0) {
                                    if (tq != null && tq.getKey() != null) {
                                        at.delete(tq.getKey());
                                    }
                                } else {
                                    log.info("update tag:" + tq + " remove key:" + k);
                                    at.put(tq); // update keys list
                                }
                            }
                        }
                        result = true;
                    } catch (Exception e) {
                        log.warning("setTags err:" + e);
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
        if (tags != null && tags.size() > 0) {
            for (String tag : tags) {
                TagQuestion tq = Datastore.query(e).filter(e.tag.equal(tag)).asSingle();
                if (tq != null && tq.getKeys() != null) {
                    List<Key> keys = tq.getKeys();
                    // todo: limit to 1000 max at a time
                    List<Question> qs = dq.get(keys);
                    result.addAll(qs);
                }
            }
        }
        return result;
    }
}
