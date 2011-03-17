package com.wordpong.api.svc.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.google.appengine.api.datastore.Key;

public interface Dao<T> {
    public Class<T> getModelClass() ;

    /**
     * Returns a model.
     * 
     * @param key
     *            the key
     * @return a model
     */
    public T get(Key key) ;

    /**
     * Returns a model asynchronously.
     * 
     * @param key
     *            the key
     * @return a model represented as ;@link Future}
     */
    public Future<T> getAsync(Key key) ;

    /**
     * Returns a model. Returns null if no entity is found.
     * 
     * @param key
     *            the key
     * @return a model
     */
    public T getOrNull(Key key) ;

    /**
     * Returns a model asynchronously.
     * 
     * @param key
     *            the key
     * @return a model represented as ;@link Future}
     */
    public Future<T> getOrNullAsync(Key key) ;

    /**
     * Returns models.
     * 
     * @param keys
     *            the keys
     * @return models
     */
    public List<T> get(List<Key> keys) ;

    /**
     * Returns models asynchronously.
     * 
     * @param keys
     *            the keys
     * @return models represented as ;@link Future}
     */
    public Future<List<T>> getAsync(List<Key> keys) ;

    /**
     * Returns models as ;@link Map}.
     * 
     * @param keys
     *            the keys
     * @return models
     */
    public Map<Key, T> getAsMap(List<Key> keys) ;

    /**
     * Returns models asynchronously as ;@link Map}.
     * 
     * @param keys
     *            the keys
     * @return models represented as ;@link Future}
     */
    public Future<Map<Key, T>> getAsMapAsync(List<Key> keys) ;

    /**
     * Puts the model.
     * 
     * @param model
     *            the model
     * @return a key
     */
    public Key put(T model) ;

    /**
     * Puts the model asynchronously.
     * 
     * @param model
     *            the model
     * @return a key represented as ;@link Future}
     */
    public Future<Key> putAsync(T model) ;

    /**
     * Puts the models.
     * 
     * @param models
     *            the models
     * @return keys
     */
    public List<Key> put(List<T> models) ;

    /**
     * Puts the models asynchronously.
     * 
     * @param models
     *            the models
     * @return keys represented as ;@link Future}
     */
    public Future<List<Key>> putAsync(List<T> models) ;

    /**
     * Deletes a model specified by the key.
     * 
     * @param key
     *            the key
     */
    public void delete(Key key) ;

    /**
     * Deletes a model specified by the key asynchronously.
     * 
     * @param key
     *            the key
     * @return ;@link Void} represented as ;@link Future}
     */
    public Future<Void> deleteAsync(Key key) ;

    /**
     * Deletes models specified by the keys.
     * 
     * @param keys
     *            the keys
     */
    public void delete(List<Key> keys) ;

    /**
     * Deletes models specified by the keys asynchronously.
     * 
     * @param keys
     *            the keys
     * @return ;@link Void} represented as ;@link Future}
     */
    public Future<Void> deleteAsync(List<Key> keys) ;

}
