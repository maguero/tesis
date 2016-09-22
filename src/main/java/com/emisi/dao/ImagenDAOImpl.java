package com.emisi.dao;

import com.emisi.model.Imagen;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.List;

/**
 * @author mjaguero
 * 
 */
public class ImagenDAOImpl extends GenericDAOImpl<Imagen> {

	// metodos para busquedas

	public List<Imagen> findByRule(final String ruleName) {

		return (List<Imagen>) getHibernateTemplate().execute(new HibernateCallback() {
            public List<Imagen> doInHibernate(Session session) throws HibernateException,
                    SQLException {
            	
            	SQLQuery query = session.createSQLQuery("SELECT * from imagen WHERE idImagen IN (" +
															"SELECT imagen_ob.busquedaPorRegla(:ruleName) FROM DUAL)")
            			.addEntity("imagen",Imagen.class);
            	query.setParameter("ruleName", ruleName);

                return query.list();
            }
        });
	}

}
