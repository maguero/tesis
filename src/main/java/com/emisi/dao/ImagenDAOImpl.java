package com.emisi.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.emisi.model.Imagen;

/**
 * @author mjaguero
 * 
 */
public class ImagenDAOImpl extends GenericDAOImpl<Imagen> {

	// metodos para busquedas

	public Imagen find(final Serializable id) {
		return (Imagen) getHibernateTemplate().execute(new HibernateCallback() {
            public Imagen doInHibernate(Session session) throws HibernateException,
                    SQLException {
            	
            	//Join example with addEntity and addJoin
            	// SELECT {serie.*}, {imagen.*}  FROM serie_obj_table serie JOIN TABLE(serie.imagenes) imagen on imagen.fkSerie = serie.idSerie
            	SQLQuery query = session.createSQLQuery("select i.* from estudio e join table(e.series) s on e.idEstudio = s.fkEstudio join table(s.imagenes) i on s.idSerie = i.fkSerie where i.idImagen = :id")
            			.addEntity("imagen",Imagen.class);
            	
            	query.setParameter("id", id);
            	
            	List<Object> rows = query.list();
            	
                return ((rows != null) && !rows.isEmpty()) ? (Imagen) rows.get(0) : null;
            }
        });
	}

	/* (non-Javadoc)
	 * @see com.emisi.dao.GenericDAOImpl#find(java.io.Serializable, java.lang.Class)
	 */
	@Override
	public Imagen find(Serializable id, Class<? extends Imagen> persistentType) {
		return find(id);
	}

	/* (non-Javadoc)
	 * @see com.emisi.dao.GenericDAOImpl#findAll(java.lang.Class)
	 */
	@Override
	public List<Imagen> findAll(Class clazz) {
		return findAll();
	}

	@SuppressWarnings("unchecked")
	public List<Imagen> findAll() {
		
		return (List<Imagen>) getHibernateTemplate().execute(new HibernateCallback() {
            public List<Imagen> doInHibernate(Session session) throws HibernateException,
                    SQLException {
            	
            	//Join example with addEntity and addJoin
            	// SELECT {serie.*}, {imagen.*}  FROM serie_obj_table serie JOIN TABLE(serie.imagenes) imagen on imagen.fkSerie = serie.idSerie
            	SQLQuery query = session.createSQLQuery("select i.* from estudio e join table(e.series) s on e.idEstudio = s.fkEstudio join table(s.imagenes) i on s.idSerie = i.fkSerie")
            			.addEntity("i",Imagen.class);
            	
                return (List<Imagen>) query.list();
            }
        });
		
	}
	
}
