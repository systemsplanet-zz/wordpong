package com.wordpong.api.svc.dao;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.concurrent.Future;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

public class DaoBase<T> {
    protected Class<T> clazz;

    // Magic to get the class that is subclassing this class into clazz
    @SuppressWarnings("unchecked")
    public DaoBase() {
        clazz = ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    // Key key = u.getKey();
    public T read(Key key) {
        T u = Datastore.get(clazz, key);
        return u;
    }

    public List<T> readList(List<Key> keys) {
        List<T> us = Datastore.get(clazz, keys);
        return us;
    }

    public Future<List<T>> readListAsync(List<Key> keys) {
        Future<List<T>> futures = Datastore.getAsync(clazz, keys);
        return futures;
    }

    public void update(T u) {
        Datastore.put(u);
    }

    // use Key key= future.get() to get the result
    public Future<Key> updateAsync(T u) {
        Future<Key> future = Datastore.putAsync(u);
        return future;
    }

    public void updateList(List<T> us) {
        Datastore.put(us);
    }

    public void delete(Key k) {
        Datastore.delete(k);
    }

    // use future.get() to get the result
    public Future<Void> deleteAsync(Key k) {
        Future<Void> future = Datastore.deleteAsync(k);
        return future;
    }

    public void deleteList(List<Key> ks) {
        Datastore.delete(ks);
    }

    // use Key key = future.get(); to get result
    public Future<Key> saveAsync(T u) throws DaoException {
        Future<Key> future = Datastore.putAsync(u);
        return future;
    }
}
