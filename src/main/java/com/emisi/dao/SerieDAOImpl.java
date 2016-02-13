package com.emisi.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.emisi.model.Imagen;
import com.emisi.model.Serie;

/**
 * @author mjaguero
 * 
 */
public class SerieDAOImpl extends HibernateDaoSupport {

	// metodos para busquedas

	public Serie find(final Serializable id) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Serie> findAll() {
		
		return (List<Serie>) getHibernateTemplate().execute(new HibernateCallback() {
            public List<Serie> doInHibernate(Session session) throws HibernateException,
                    SQLException {
            	
            	//Join example with addEntity and addJoin
            	// SELECT {serie.*}, {imagen.*}  FROM serie_obj_table serie JOIN TABLE(serie.imagenes) imagen on imagen.fkSerie = serie.idSerie
            	SQLQuery query = session.createSQLQuery("SELECT {serie.*}, {imagen.*}  FROM serie_obj_table serie JOIN TABLE(serie.imagenes) imagen on imagen.fkSerie = serie.idSerie")
            			.addEntity("serie",Serie.class)
            			.addEntity("imagen",Imagen.class);
            	
            	List<Object[]> rows = query.list();
            	
            	for (Object[] row : rows) {
            		for(Object obj : row) {
            			System.out.print(obj + "::");
            		}
            		System.out.println("\n");
            	}
            	
            	//Above join returns both Employee and Address Objects in the array
            	Map<String, Serie> series = new HashMap<String, Serie>();
            	for (Object[] row : rows) {
            		Serie serie = (Serie) row[0];
            		Imagen img = (Imagen) row[1];
            		
            		if (series.get(serie.getIdSerie()) == null) {
            			//serie.setImagenes(new ArrayList<Imagen>());
            			serie.getImagenes().add(img);
            			series.put(serie.getIdSerie(), serie);
            		} else {
            			series.get(serie.getIdSerie()).getImagenes().add(img);
            		}
            		
            	}
            	
            	
                return new ArrayList<Serie>(series.values());
            }
        });
		
	}
	
}
