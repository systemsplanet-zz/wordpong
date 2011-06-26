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
            q.setDescription("How are you? (public cliches)");
            q.setIntimacyLevel(Question.INTIMACY_CLICHES);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
            qs.clear();
            qs.add("What do you think about the weather we're having?");
            qs.add("How is your day going?");
            qs.add("How was your weekend?");
            qs.add("Anything exciting going on with you?");
            q.setQuestions(qs);
            tags.clear();
            tags.add("favorites");
            tags.add("personal");
            q.setTitle("How are you?");
            q.setUser(u.getKey());
            q.setVisibility(Question.VISIBILITY_PUBLIC);
            setTags(q, tags);

            
            q = new Question();
            q.setDescription("Favorite Colors (public facts)");
            q.setIntimacyLevel(Question.INTIMACY_FACTS);
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
            q.setVisibility(Question.VISIBILITY_PUBLIC);
            setTags(q, tags);
                        
            
            q = new Question();
            q.setDescription("Favorite Dates (public facts)");
            q.setIntimacyLevel(Question.INTIMACY_FACTS);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
            qs.clear();
            qs.add("Birth Date?");
            qs.add("Age at first Job?");
            qs.add("Age at Highschool graduation?");
            qs.add("Age when you first drove a car?");
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
            q.setDescription("Favorite Places (public facts)");
            q.setIntimacyLevel(Question.INTIMACY_FACTS);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
            qs.clear();
            qs.add("Birth Place?");
            qs.add("High School Location?");
            qs.add("Work Place?");
            qs.add("Place where you grew up?");
            q.setQuestions(qs);
            tags.clear();
            tags.add("favorites");
            tags.add("personal");
            tags.add("places");
            q.setTitle("Favorite Places");
            q.setUser(u.getKey());
            q.setVisibility(Question.VISIBILITY_PUBLIC);
            setTags(q, tags);
          
            
            q = new Question();
            q.setDescription("Big Issues (public opinions)");
            q.setIntimacyLevel(Question.INTIMACY_OPINIONS);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
            qs.clear();
            qs.add("Religion?");
            qs.add("Politics?");
            qs.add("Taxes?");
            qs.add("Homelessness?");
            q.setQuestions(qs);
            tags.clear();
            tags.add("personal");
            tags.add("big issues");
            tags.add("religion");
            tags.add("politics");
            tags.add("taxes");
            tags.add("homelessness");
            q.setTitle("Big Issues");
            q.setUser(u.getKey());
            q.setVisibility(Question.VISIBILITY_PUBLIC);
            setTags(q, tags);

            q = new Question();
            q.setDescription("Dream Lifestyle (public hopes)");
            q.setIntimacyLevel(Question.INTIMACY_HOPES);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
            qs.clear();
            qs.add("Dream Job?");
            qs.add("Dream Vacation?");
            qs.add("Dream Retirement?");
            qs.add("Dream Lifestyle?");
            q.setQuestions(qs);
            tags.clear();
            tags.add("personal");
            tags.add("dreams");
            tags.add("lifestyle");
            tags.add("hopes");
            q.setTitle("Dream Lifestyle");
            q.setUser(u.getKey());
            q.setVisibility(Question.VISIBILITY_PUBLIC);
            setTags(q, tags);

            
            q = new Question();
            q.setDescription("Feelings About... (public feelings)");
            q.setIntimacyLevel(Question.INTIMACY_FEELINGS);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
            qs.clear();
            qs.add("Your childhood?");
            qs.add("Your parents?");
            qs.add("Your life?");
            qs.add("Your future?");
            q.setQuestions(qs);
            tags.clear();
            tags.add("personal");
            tags.add("feelings");
            tags.add("life");
            tags.add("childhood");
            tags.add("parents");
            tags.add("future");
            q.setTitle("Feelings about...");
            q.setUser(u.getKey());
            q.setVisibility(Question.VISIBILITY_PUBLIC);
            setTags(q, tags);

            
            q = new Question();
            q.setDescription("Biggest Fears (public fears)");
            q.setIntimacyLevel(Question.INTIMACY_FEARS);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
            qs.clear();
            qs.add("Biggest Fear?");
            qs.add("Afraid of dieing?");
            qs.add("Afraid of failing?");
            qs.add("Fear of heights?");
            q.setQuestions(qs);
            tags.clear();
            tags.add("personal");
            tags.add("fears");
            tags.add("death");
            q.setTitle("Biggest Fears");
            q.setUser(u.getKey());
            q.setVisibility(Question.VISIBILITY_PUBLIC);
            setTags(q, tags);

            q = new Question();
            q.setDescription("Needs (public needs)");
            q.setIntimacyLevel(Question.INTIMACY_NEEDS);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
            qs.clear();
            qs.add("What do you need the most?");
            qs.add("Money Needs?");
            qs.add("Shelter Needs?");
            qs.add("Relationship Needs?");
            q.setQuestions(qs);
            tags.clear();
            tags.add("personal");
            tags.add("needs");
            tags.add("money");
            tags.add("relationship");
            tags.add("shelter");
            q.setTitle("Needs");
            q.setUser(u.getKey());
            q.setVisibility(Question.VISIBILITY_PUBLIC);
            setTags(q, tags);

            
            
            q = new Question();
            q.setDescription("Favorite Alcohol (private opinions)");
            q.setIntimacyLevel(Question.INTIMACY_OPINIONS);
            q.setLocaleString(LocaleDisplay.LOCALE_EN_US);
            qs.clear();
            qs.add("Beer?");
            qs.add("Wine?");
            qs.add("Mixed Drink?");
            qs.add("Shooter?");
            q.setQuestions(qs);
            tags.clear();
            tags.add("personal");
            tags.add("favorites");
            tags.add("personal");
            tags.add("food");
            tags.add("liquid");
            tags.add("alcohol");
            q.setTitle("Favorite Alcohol");
            q.setUser(u.getKey());
            q.setVisibility(Question.VISIBILITY_PRIVATE);
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
