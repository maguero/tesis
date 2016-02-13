/**
 * 
 */
package com.emisi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.emisi.model.Estudio;

/**
 * @author maguero
 *
 */
public class EstudioDAOImpl extends GenericDAOImpl<Estudio> {

	public void insert(Estudio estudio) {
		
		getHibernateTemplate().execute(new HibernateCallback() {
            public Integer doInHibernate(Session session) throws HibernateException,
                    SQLException {
            	
            	Connection con = session.connection();  

            	/**
            	* Amend to include your parameters and proc
            	*/          
            	CallableStatement cs = con.prepareCall( "exec ord_dicom.setDataModel()");

            	cs.execute();
            	
            	String update = "INSERT INTO estudio values ("
            			+ "estudio_ob('e3','desc3','p3',"
            				+ "series_ob(serie_ob('s3','desc3','e3',"
            					+ "imagenes_ob("
            						+ "imagen_ob('I9', 's3','000009.dcm'),"
            						+ "imagen_ob('I10', 's3','000010.dcm'),"
            						+ "imagen_ob('I11', 's3','000011.dcm')"
            						+ ")))))";
            	
            	SQLQuery query = session.createSQLQuery(update);
                return query.executeUpdate();
            }
        });
		
	}

	
	
}
