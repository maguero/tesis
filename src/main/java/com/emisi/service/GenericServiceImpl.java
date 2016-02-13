package com.emisi.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.emisi.dao.GenericDAO;

/**
 * @author mjaguero
 * 
 */
public class GenericServiceImpl<CLAZZ> implements
		GenericService<CLAZZ> {

	// Propiedades
	private GenericDAO<CLAZZ> dao;
	private Class<CLAZZ> clase;

	// Metodos

	@Override
	@Transactional
	public void delete(CLAZZ entity) {
		dao.delete(entity);
	}

	@Override
	public CLAZZ findById(Serializable id) {
		return (dao.find(id, clase));
	}

	@Override
	@Transactional
	public void save(CLAZZ entity) {
		dao.save(entity);
	}

	public List<CLAZZ> findAll() {
		return dao.findAll(clase);
	}

	// Getters y Setters
	public GenericDAO<CLAZZ> getDao() {
		return dao;
	}

	public void setDao(GenericDAO<CLAZZ> dao) {
		this.dao = dao;
	}

	public Class<CLAZZ> getClase() {
		return clase;
	}

	public void setClase(Class<CLAZZ> clase) {
		this.clase = clase;
	}

}
