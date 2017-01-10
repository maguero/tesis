package com.emisi.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author mjaguero
 * 
 */
public class GenericDAOImpl<T> extends HibernateDaoSupport implements GenericDAO<T> {

	// metodos para busquedas

	@SuppressWarnings("unchecked")
	public T find(final Serializable id, final Class<? extends T> persistentType) {
		return (T) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Object result = session.get(persistentType, id);
				if (result != null) {
					getHibernateTemplate().evict(result);
				}
				return result;
			}
		});
	}

	public Serializable getId(T object) throws Exception {
		ClassMetadata metaData = this.getSessionFactory().getClassMetadata(
				object.getClass());
		if (metaData == null) {
			// If an object is not supported
			throw new Exception("No se puede retornar el id del objecto. "
					+ "Could not retrieve object's id. Unsupported "
					+ "object type " + object.getClass());
		} else {
			Serializable id = metaData.getIdentifier(object, EntityMode.POJO);
			return id;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(final Class clazz) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                Criteria criteria = session.createCriteria(clazz);
                return criteria.list();
            }
        });
	}
	
	@SuppressWarnings("unused")
	protected List executeQuery(Query query, List parameters,
			Map<String, Object> namedParameters, int maxResult) {
		setQueryParameters(query, parameters, namedParameters);

		if (maxResult > 0) {
			query.setMaxResults(maxResult);
		}
		return query.list();
	}
	
	private void setQueryParameters(Query query, List parameters,
			Map<String, Object> namedParameters) {
		int i = 0;
		for (Object parameter : parameters) {
			query.setParameter(i++, parameter);
		}
		for (Iterator<String> iterator = namedParameters.keySet().iterator(); iterator
				.hasNext();) {
			String name = iterator.next();
			Object value = namedParameters.get(name);
			if (value instanceof Collection) {
				query.setParameterList(name, (Collection) value);
			} else {
				query.setParameter(name, value);
			}
		}
	}

	// metodos para persistir

	public void delete(T object) {
		getHibernateTemplate().delete(object);
	}

	public void save(T object) {
		getHibernateTemplate().saveOrUpdate(object);
	}

	// otros metodos

	public void clear() {
		getSession(false).clear();
	}

	public void flush() {
		getSession(false).flush();
	}

	public Object merge(T object) {
		return getHibernateTemplate().merge(object);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(final String namedQuery, final Map<String, Object> params) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public List<Object> doInHibernate(Session session)
					throws HibernateException, SQLException {
				final Query query = session.getNamedQuery(namedQuery);
				for (String key : params.keySet()) {
					query.setString(key, (String) params.get(key));
				}
				return query.list();
			}
		});
	}
}
