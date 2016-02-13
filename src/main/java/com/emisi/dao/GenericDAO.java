/**
 * 
 */
package com.emisi.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author mjaguero
 * 
 */
public interface GenericDAO<T> {

	// metodos para busquedas

	T find(Serializable id, Class<? extends T> persistentType);

	Serializable getId(T object) throws Exception;
	
	// List<T> find(NamedQuery<T> query);
	
	// List<T> find(CriteriaQuery<T> query);

    List<T> findAll(Class clazz);

	// metodos para persistir

	void save(T object);

	void delete(T object);

	// otros metodos

	void flush();

	Object merge(final T object);

	void clear();
}