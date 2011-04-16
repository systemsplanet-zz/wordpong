package com.wordpong.api.svc.dao.transact;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.EntityNotFoundRuntimeException;
import org.slim3.datastore.EntityQuery;
import org.slim3.datastore.GlobalTransaction;
import org.slim3.datastore.KindlessQuery;
import org.slim3.datastore.ModelMeta;
import org.slim3.datastore.ModelQuery;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.google.apphosting.api.DeadlineExceededException;
import com.google.common.base.Predicate;

/**
 * This class wraps the slim3 GlobalTransaction class 
 * to provide support for simple transactional predicates
 * 
 * @author mlawrence
 * 
 */
public class Atomic {
    private GlobalTransaction gtx;

    private Atomic() {
    }

    private Atomic(GlobalTransaction g) {
        this.gtx = g;
    }

    private static final Logger log = Logger.getLogger(Atomic.class.getName());

    /**
     * Call the predicate in a transaction
     * 
     * @param p
     *            - predicate to run
     * @param maxRetries
     *            -max times to retry in error
     * @throws Exception
     *             - exception if unable to complete
     */
    public static void transact(Predicate<Atomic> p, int maxRetries) throws Exception {
        int retries = maxRetries;
        boolean success = false;
        while (true) {
            GlobalTransaction gt = Datastore.beginGlobalTransaction();
            Atomic at = new Atomic(gt);
            Exception err = new Exception("unknown error");
            try {
                success = p.apply(at);
                if (success) {
                    at.commit();
                    success = at.isActive() == false;
                }
            } catch (ConcurrentModificationException e) {
                err = e;
                log.warning("concurrent modification for predicate:" + p + " err:" + e.getMessage());
                e.printStackTrace();
                success = false;
            } catch (DeadlineExceededException e) {
                err = e;
                log.warning("timeout processing predicate:" + p + " err:" + e.getMessage());
                e.printStackTrace();
                success = false;
            } catch (Exception e) {
                err = e;
                log.warning("unable to process predicate:" + p + " err:" + e.getMessage());
                e.printStackTrace();
                success = false;
            } finally {
                if (at.isActive()) {
                    at.rollback();
                    success = false;
                }
            }
            if (success == false) {
                if (retries > 0) {
                    log.warning("retrying predicate:" + p);
                    retries--;
                } else {
                    throw err;
                }
            } else {
                break;
            }
        }
    }

    // DELEGATE METHODS FOLLOW
    public void commit() {
        gtx.commit();
    }

    public void delete(Iterable<Key> arg0) throws NullPointerException, ConcurrentModificationException {
        gtx.delete(arg0);
    }

    public void delete(Key... keys) throws ConcurrentModificationException {
        gtx.delete(keys);
    }

    public void delete(Key key) throws NullPointerException, ConcurrentModificationException {
        gtx.delete(key);
    }

    public void deleteAll(Key ancestorKey) throws NullPointerException, ConcurrentModificationException {
        gtx.deleteAll(ancestorKey);
    }

    public boolean equals(Object obj) {
        return gtx.equals(obj);
    }

    public <M> List<M> get(Class<M> modelClass, Iterable<Key> keys) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.get(modelClass, keys);
    }

    public <M> M get(Class<M> modelClass, Key key, long version) throws NullPointerException, EntityNotFoundRuntimeException, ConcurrentModificationException, IllegalStateException {
        return gtx.get(modelClass, key, version);
    }

    public <M> List<M> get(Class<M> modelClass, Key... keys) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.get(modelClass, keys);
    }

    public <M> M get(Class<M> modelClass, Key key) throws NullPointerException, EntityNotFoundRuntimeException, ConcurrentModificationException, IllegalStateException {
        return gtx.get(modelClass, key);
    }

    public List<Entity> get(Iterable<Key> keys) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.get(keys);
    }

    public List<Entity> get(Key... keys) throws ConcurrentModificationException, IllegalStateException {
        return gtx.get(keys);
    }

    public Entity get(Key key) throws NullPointerException, EntityNotFoundRuntimeException, ConcurrentModificationException, IllegalStateException {
        return gtx.get(key);
    }

    public <M> List<M> get(ModelMeta<M> modelMeta, Iterable<Key> keys) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.get(modelMeta, keys);
    }

    public <M> M get(ModelMeta<M> modelMeta, Key key, long version) throws NullPointerException, EntityNotFoundRuntimeException, ConcurrentModificationException, IllegalStateException {
        return gtx.get(modelMeta, key, version);
    }

    public <M> List<M> get(ModelMeta<M> modelMeta, Key... keys) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.get(modelMeta, keys);
    }

    public <M> M get(ModelMeta<M> modelMeta, Key key) throws NullPointerException, EntityNotFoundRuntimeException, ConcurrentModificationException, IllegalStateException {
        return gtx.get(modelMeta, key);
    }

    public <M> Map<Key, M> getAsMap(Class<M> modelClass, Iterable<Key> keys) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.getAsMap(modelClass, keys);
    }

    public <M> Map<Key, M> getAsMap(Class<M> modelClass, Key... keys) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.getAsMap(modelClass, keys);
    }

    public Map<Key, Entity> getAsMap(Iterable<Key> arg0) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.getAsMap(arg0);
    }

    public Map<Key, Entity> getAsMap(Key... keys) throws ConcurrentModificationException, IllegalStateException {
        return gtx.getAsMap(keys);
    }

    public <M> Map<Key, M> getAsMap(ModelMeta<M> modelMeta, Iterable<Key> keys) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.getAsMap(modelMeta, keys);
    }

    public <M> Map<Key, M> getAsMap(ModelMeta<M> modelMeta, Key... keys) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.getAsMap(modelMeta, keys);
    }

    public String getId() throws IllegalStateException {
        return gtx.getId();
    }

    public Transaction getLocalTransaction() {
        return gtx.getLocalTransaction();
    }

    public <M> M getOrNull(Class<M> modelClass, Key key) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.getOrNull(modelClass, key);
    }

    public Entity getOrNull(Key key) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.getOrNull(key);
    }

    public <M> M getOrNull(ModelMeta<M> modelMeta, Key key) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.getOrNull(modelMeta, key);
    }

    public int hashCode() {
        return gtx.hashCode();
    }

    public boolean isActive() {
        return gtx.isActive();
    }

    public Key put(Entity entity) throws NullPointerException, IllegalArgumentException, ConcurrentModificationException {
        return gtx.put(entity);
    }

    public List<Key> put(Iterable<?> arg0) throws NullPointerException, IllegalArgumentException, ConcurrentModificationException {
        return gtx.put(arg0);
    }

    public List<Key> put(Object... models) throws IllegalArgumentException, ConcurrentModificationException {
        return gtx.put(models);
    }

    public Key put(Object model) throws NullPointerException, IllegalArgumentException, ConcurrentModificationException {
        return gtx.put(model);
    }

    public <M> ModelQuery<M> query(Class<M> modelClass, Key ancestorKey) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.query(modelClass, ancestorKey);
    }

    public KindlessQuery query(Key ancestorKey) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.query(ancestorKey);
    }

    public <M> ModelQuery<M> query(ModelMeta<M> modelMeta, Key ancestorKey) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.query(modelMeta, ancestorKey);
    }

    public EntityQuery query(String kind, Key ancestorKey) throws NullPointerException, ConcurrentModificationException, IllegalStateException {
        return gtx.query(kind, ancestorKey);
    }

    public void rollback() {
        gtx.rollback();
    }

    public String toString() {
        return gtx.toString();
    }
}
