package com.wordpong.cmn.db.objectify;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Transient;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.helper.DAOBase;

/**
 * For performances and security issues: escapeFilter, escapeSortOrders,
 * escapeProperties and escapeFetchLimit should be over-ridden in entity
 * specific DAO class.
 */
public class ObjectifyGenericDao<T> extends DAOBase {
	static final int FETCH_LIMIT = 200;
	static final int BAD_MODIFIERS = Modifier.FINAL | Modifier.STATIC
			| Modifier.TRANSIENT;

	static {
		// Register all your entity classes here
		// ObjectifyService.register(MyDomain.class);
		// ...
	}

	protected Class<T> clazz;

	// Magic to get the class that is subclassing this class into clazz
	@SuppressWarnings("unchecked")
	public ObjectifyGenericDao() {
		clazz = ((Class) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0]);
	}

	public Key<T> put(T entity) {
		return ofy().put(entity);
	}

	public Map<Key<T>, T> putAll(Iterable<T> entities) {
		return ofy().put(entities);
	}

	public void delete(T entity) {
		ofy().delete(entity);
	}

	public void deleteKey(Key<T> entityKey) {
		ofy().delete(entityKey);
	}

	public void deleteAll(Iterable<T> entities) {
		ofy().delete(entities);
	}

	public void deleteKeys(Iterable<Key<T>> keys) {
		ofy().delete(keys);
	}

	public T get(Long id) throws EntityNotFoundException {
		return ofy().get(this.clazz, id);
	}

	public T get(Key<T> key) throws EntityNotFoundException {
		return ofy().get(key);
	}

	/**
	 * Convenience method to get all objects matching a single property
	 * 
	 * @param propName
	 * @param propValue
	 * @return T matching Object
	 */
	public T getByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return q.get();
	}

	public List<T> listByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return q.list();
	}

	public List<Key<T>> listKeysByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return q.listKeys();
	}

	public T getByExample(T exampleObj) {
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		QueryResultIterator<T> iterableResults = queryByExample.iterator();
		if (!iterableResults.hasNext()) {
			return null;
		}
		T obj = iterableResults.next();
		if (iterableResults.hasNext())
			throw new RuntimeException("Too many results");
		return obj;
	}

	public List<T> listByExample(T exampleObj) {
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		return queryByExample.list();
	}

	public List<Key<T>> getKeysByExample(T exampleObj) {
		Query<T> q = buildQueryByExample(exampleObj);
		return q.listKeys();
	}

	/*
	 * provided by objectify QueryWrapper now: public List<T> list() private
	 * List<T> asList(Iterable<T> iterable) { ArrayList<T> list = new
	 * ArrayList<T>(); for (T t : iterable) { list.add(t); } return list; }
	 * 
	 * public List<Key<T>> listKeys() private List<Key<T>>
	 * asKeyList(Iterable<Key<T>> iterableKeys) { ArrayList<Key<T>> keys = new
	 * ArrayList<Key<T>>(); for (Key<T> key : iterableKeys) { keys.add(key); }
	 * return keys; }
	 */
	private Query<T> buildQueryByExample(T exampleObj) {
		Query<T> q = ofy().query(clazz);

		// Add all non-null properties to query filter
		for (Field field : clazz.getDeclaredFields()) {
			// Ignore transient, embedded, array, and collection properties
			if (field.isAnnotationPresent(Transient.class)
					|| (field.isAnnotationPresent(Embedded.class))
					|| (field.getType().isArray())
					|| (Collection.class.isAssignableFrom(field.getType()))
					|| ((field.getModifiers() & BAD_MODIFIERS) != 0))
				continue;

			field.setAccessible(true);

			Object value;
			try {
				value = field.get(exampleObj);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			if (value != null) {
				q.filter(field.getName(), value);
			}
		}

		return q;
	}

	// ADDITIONS

	public T updateProperties(T entity, Iterable<Property> properties)
			throws Exception {
		List<Property> eps = getProperties(properties);
		for (Property p : eps) {
			entity = updateProperty(entity, p);
		}
		return entity;
	}

	public T updateProperty(T entity, Property property) throws Exception {
		String[] fieldNames = property.getPropertyName().split("\\.");
		int len = fieldNames.length;
		Object entityField = entity;
		for (int i = 0; i < len - 1; i++) {
			entityField = getField(entityField, fieldNames[i]);
		}
		setField(entityField, fieldNames[len - 1], property.getPropertyValue());
		return entity;
	}

	public Object getField(Object obj, String fieldName) throws Exception {
		String methName = "get" + fieldName.toUpperCase().charAt(0)
				+ fieldName.substring(1);
		Method meth = obj.getClass().getMethod(methName);
		return meth.invoke(obj);
	}

	@SuppressWarnings("unchecked")
	public void setField(Object obj, String fieldName, Object value)
			throws Exception {
		String methName = "set" + fieldName.toUpperCase().charAt(0)
				+ fieldName.substring(1);
		Class parametersTypes[] = new Class[1];
		parametersTypes[0] = value.getClass();
		Method meth = obj.getClass().getMethod(methName, parametersTypes);
		Object argList[] = new Object[1];
		argList[0] = value;
		meth.invoke(obj, argList);
	}

	/**
	 * Convenience method to build all possible get Queries
	 * 
	 * @param filters
	 *            :query filters
	 * @param sortOrders
	 *            : query sort orders
	 * @param limit
	 *            : number of maximum fetched results
	 * @return Query
	 */

	public Query<T> buildQuery(Iterable<Filter> filters,
			Iterable<String> sortOrders, int limit) {
		Query<T> q = ofy().query(clazz);

		List<Filter> escapedFilters = getFilters(filters);
		List<String> escapedSortOrders = getSortOrders(sortOrders);
		int escapedLimit = getFetchLimit(limit);

		for (Filter f : escapedFilters) {
			q.filter(f.getFilterCondition(), f.getFilterValue());
		}
		for (String so : escapedSortOrders) {
			q.order(so);
		}
		q.limit(escapedLimit);
		return q;
	}

	/**
	 * Convenience method to get all objects for all possible get Queries
	 * 
	 * @param filters
	 *            :query filters
	 * @param sortOrders
	 *            : query sort orders
	 * @param limit
	 *            : number of maximum fetched objects
	 * @return List
	 */

	public List<T> listByQuery(Iterable<Filter> filters,
			Iterable<String> sortOrders, int limit) {
		Query<T> q = buildQuery(filters, sortOrders, limit);
		return q.list();
	}

	/**
	 * Convenience method to get all object keys for all possible get Queries
	 * 
	 * @param filters
	 *            :query filters
	 * @param sortOrders
	 *            : query sort orders
	 * @param limit
	 *            : number of maximum fetched keys
	 * @return List<Key>
	 */

	public List<Key<T>> listKeysByQuery(Iterable<Filter> filters,
			Iterable<String> sortOrders, int limit) {
		Query<T> q = buildQuery(filters, sortOrders, limit);
		return q.listKeys();
	}

	// Override these getters based on the Entity
	public List<Property> getProperties(Iterable<Property> properties) {
		ArrayList<Property> eps = new ArrayList<Property>();
		for (Property p : properties) {
			eps.add(p);
		}
		return eps;
	}

	public List<String> getSortOrders(Iterable<String> sortOrders) {
		List<String> esos = new ArrayList<String>();
		for (String so : sortOrders) {
			esos.add(so);
		}

		return esos;
	}

	public int getFetchLimit(int limit) {
		int classSpecificLimit = FETCH_LIMIT;
		if (limit >= classSpecificLimit) {
			return classSpecificLimit;
		}
		return limit;
	}

	public List<Filter> getFilters(Iterable<Filter> filters) {
		List<Filter> efs = new ArrayList<Filter>();
		for (Filter f : filters) {
			efs.add(f);
		}
		return efs;
	}

}