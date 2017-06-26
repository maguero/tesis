package com.emisi.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Servicio Generico encargado de la persistencia y consulta sobre los objetos de dominio.
 * C: Clase principal, objecto de dominio.
 * @author mjaguero
 *
 */
public interface GenericService<C> {

	/**
	 * Persiste una entidad en la base de datos
	 * @param entity
	 */
	void save(C entity);
	
	/**
	 * Elimina una entidad de la base de datos
	 * @param entity
	 */
	void delete(C entity);
	
	/**
	 * Busca una entidad en la base de datos por su id.
	 * @param id
	 * @return
	 */
	C findById(Serializable id);
	
	/**
	 * Retorna todas las entidades.
	 * @return
	 */
	List<C> findAll();
	
	/**
	 * Consulta a traves de una NamedQuery
	 * @param nombre
	 * @param parametros
	 * @return
	 */
	List<C> findByNamedQuery(String nombre, final Map<String, Object> parametros);
	
	/**
	 * Consulta a traves de una NamedQuery
	 * @param nombre
	 * @param parametros
	 * @return
	 */
	//List<C> findByNamedQueryMaxResult(String nombre, int maxResult, Object... parametros);
	
	/**
	 * Consulta a traves de un Criteria
	 * @param criteria
	 * @return
	 */
	//List<C> findByCriteria(DetachedCriteria criteria);
	
}
