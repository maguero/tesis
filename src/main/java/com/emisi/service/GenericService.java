package com.emisi.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * Servicio Generico encargado de la persistencia y consulta sobre los objetos de dominio.
 * CLAZZ: Clase principal, objecto de dominio.
 * @author mjaguero
 *
 */
public interface GenericService<CLAZZ> {

	/**
	 * Persiste una entidad en la base de datos
	 * @param entity
	 */
	void save(CLAZZ entity);
	
	/**
	 * Elimina una entidad de la base de datos
	 * @param entity
	 */
	void delete(CLAZZ entity);
	
	/**
	 * Busca una entidad en la base de datos por su id.
	 * @param id
	 * @return
	 */
	CLAZZ findById(Serializable id);
	
	/**
	 * Retorna todas las entidades.
	 * @return
	 */
	List<CLAZZ> findAll();
	
	/**
	 * Consulta a traves de una NamedQuery
	 * @param nombre
	 * @param parametros
	 * @return
	 */
	//List<CLAZZ> findByNamedQuery(String nombre, Object... parametros);
	
	/**
	 * Consulta a traves de una NamedQuery
	 * @param nombre
	 * @param parametros
	 * @return
	 */
	//List<CLAZZ> findByNamedQueryMaxResult(String nombre, int maxResult, Object... parametros);
	
	/**
	 * Consulta a traves de un Criteria
	 * @param criteria
	 * @return
	 */
	//List<CLAZZ> findByCriteria(DetachedCriteria criteria);
	
}
