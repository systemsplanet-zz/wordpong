package com.wordpong.api.svc.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
/**
 * 
 * DAO base class.
 * TODO: Replace with http://slim3.googlecode.com/svn/trunk/slim3/src/main/java/org/slim3/datastore/DaoBase.java
 * on slim release 1.0.10
 * 
 */
public class DaoImpl<T> implements Dao<T>{

    /**
     * The model class.
     */
    protected Class<T> modelClass;

    /**
     * Constructor.
     */
    @SuppressWarnings("unchecked")
    public DaoImpl() {
        modelClass =
            ((Class) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0]);
    }

    /**
     * Returns the model class.
     * 
     * @return the model class
     */
    public Class<T> getModelClass() {
        return modelClass;
    }

    /**
     * Returns a model.
     * 
     * @param key
     *            the key
     * @return a model
     */
    public T get(Key key) {
        return Datastore.get(modelClass, key);
    }

    /**
     * Returns a model asynchronously.
     * 
     * @param key
     *            the key
     * @return a model represented as {@link Future}
     */
    public Future<T> getAsync(Key key) {
        return Datastore.getAsync(modelClass, key);
    }

    /**
     * Returns a model. Returns null if no entity is found.
     * 
     * @param key
     *            the key
     * @return a model
     */
    public T getOrNull(Key key) {
        return Datastore.getOrNull(modelClass, key);
    }

    /**
     * Returns a model asynchronously.
     * 
     * @param key
     *            the key
     * @return a model represented as {@link Future}
     */
    public Future<T> getOrNullAsync(Key key) {
        return Datastore.getOrNullAsync(modelClass, key);
    }

    /**
     * Returns models.
     * 
     * @param keys
     *            the keys
     * @return models
     */
    public List<T> get(List<Key> keys) {
        return Datastore.get(modelClass, keys);
    }

    /**
     * Returns models asynchronously.
     * 
     * @param keys
     *            the keys
     * @return models represented as {@link Future}
     */
    public Future<List<T>> getAsync(List<Key> keys) {
        return Datastore.getAsync(modelClass, keys);
    }

    /**
     * Returns models as {@link Map}.
     * 
     * @param keys
     *            the keys
     * @return models
     */
    public Map<Key, T> getAsMap(List<Key> keys) {
        return Datastore.getAsMap(modelClass, keys);
    }

    /**
     * Returns models asynchronously as {@link Map}.
     * 
     * @param keys
     *            the keys
     * @return models represented as {@link Future}
     */
    public Future<Map<Key, T>> getAsMapAsync(List<Key> keys) {
        return Datastore.getAsMapAsync(modelClass, keys);
    }

    /**
     * Puts the model.
     * 
     * @param model
     *            the model
     * @return a key
     */
    public Key put(T model) {
        return Datastore.put(model);
    }

    /**
     * Puts the model asynchronously.
     * 
     * @param model
     *            the model
     * @return a key represented as {@link Future}
     */
    public Future<Key> putAsync(T model) {
        return Datastore.putAsync(model);
    }

    /**
     * Puts the models.
     * 
     * @param models
     *            the models
     * @return keys
     */
    public List<Key> put(List<T> models) {
        return Datastore.put(models);
    }

    /**
     * Puts the models asynchronously.
     * 
     * @param models
     *            the models
     * @return keys represented as {@link Future}
     */
    public Future<List<Key>> putAsync(List<T> models) {
        return Datastore.putAsync(models);
    }

    /**
     * Deletes a model specified by the key.
     * 
     * @param key
     *            the key
     */
    public void delete(Key key) {
        Datastore.delete(key);
    }

    /**
     * Deletes a model specified by the key asynchronously.
     * 
     * @param key
     *            the key
     * @return {@link Void} represented as {@link Future}
     */
    public Future<Void> deleteAsync(Key key) {
        return Datastore.deleteAsync(key);
    }

    /**
     * Deletes models specified by the keys.
     * 
     * @param keys
     *            the keys
     */
    public void delete(List<Key> keys) {
        Datastore.delete(keys);
    }

    /**
     * Deletes models specified by the keys asynchronously.
     * 
     * @param keys
     *            the keys
     * @return {@link Void} represented as {@link Future}
     */
    public Future<Void> deleteAsync(List<Key> keys) {
        return Datastore.deleteAsync(keys);
    }
}