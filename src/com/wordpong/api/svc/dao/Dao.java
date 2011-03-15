package com.wordpong.api.svc.dao;

import java.util.List;
import java.util.concurrent.Future;

import com.google.appengine.api.datastore.Key;

public interface Dao<T> {
    public T read(Key key);

    public List<T> readList(List<Key> keys);

    public Future<List<T>> readListAsync(List<Key> keys);

    public void update(T u);

    public Future<Key> updateAsync(T u);

    public void updateList(List<T> us);

    public void delete(Key k);

    public Future<Void> deleteAsync(Key k);

    public void deleteList(List<Key> ks);

    public Future<Key> saveAsync(T u) throws DaoException;

}
