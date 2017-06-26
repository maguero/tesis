package com.emisi.dao;

import com.emisi.model.IdNombre;
import com.emisi.model.Imagen;
import com.emisi.model.Regla;
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

	@SuppressWarnings("unchecked")
	public List<Imagen> findByRule(final String ruleName) {

		return (List<Imagen>) getHibernateTemplate().execute(new HibernateCallback() {
            public List<Imagen> doInHibernate(Session session) throws HibernateException,
                    SQLException {

				session.createSQLQuery("call ord_dicom.setDataModel()").executeUpdate();

            	SQLQuery query = session.createSQLQuery(
            			"SELECT * from imagen img " +
								"JOIN TABLE (select imagen_ob.busquedaPorRegla('" + ruleName + "') as resultado from DUAL) imgRef " +
								"on imgRef.column_value.idImagen = img.idImagen ")
            			.addEntity("imagen",Imagen.class);

                return query.list();
            }
        });
	}

	@SuppressWarnings("unchecked")
	public List<Imagen> similar(final String idImagen) {

		return (List<Imagen>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<Imagen> doInHibernate(Session session) throws HibernateException,
					SQLException {

				session.createSQLQuery("call ord_dicom.setDataModel()").executeUpdate();

				SQLQuery query = session.createSQLQuery(
							"SELECT * from imagen img " +
							"JOIN TABLE (SELECT i.similar() FROM imagen i WHERE i.idImagen = :idImagen) imgRef " +
							"on imgRef.column_value.idImagen = img.idImagen")
						.addEntity("imagen",Imagen.class);
				query.setParameter("idImagen", idImagen);

				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Regla> getRules() {
		return (List<Regla>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<Regla> doInHibernate(Session session) throws HibernateException,
					SQLException {

				SQLQuery query = session.createSQLQuery(
						"SELECT rule_code, rule_name from rule_table");
				query.addEntity(Regla.class);

				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<IdNombre> getDiagnosticosByStudy(final String idEstudio) {
		return (List<IdNombre>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<Regla> doInHibernate(Session session) throws HibernateException,
					SQLException {

				SQLQuery query = session.createSQLQuery(
						"SELECT d$_suffix id, l nombre FROM TABLE(" +
								" SEM_MATCH('{ e:" + idEstudio + " e:diagnostico ?d . ?d rdfs:label ?l}'," +
								" SEM_Models('emisi_vm'), null," +
								" SEM_ALIASES(SEM_ALIAS('e','http://www.emisi.utn.edu.ar/tesis/')), null))")
						.addEntity("idNombre",IdNombre.class);

				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<IdNombre> getSintomasByStudy(final String idEstudio) {
		return (List<IdNombre>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<Regla> doInHibernate(Session session) throws HibernateException,
					SQLException {

				SQLQuery query = session.createSQLQuery(
						"SELECT s$_suffix id, l nombre FROM TABLE(" +
								" SEM_MATCH('{ e:" + idEstudio + " e:sintoma ?s . ?s rdfs:label ?l}'," +
								" SEM_Models('emisi_vm'), null," +
								" SEM_ALIASES(SEM_ALIAS('e','http://www.emisi.utn.edu.ar/tesis/')), null))")
						.addEntity("idNombre",IdNombre.class);

				return query.list();
			}
		});
	}

}
