/**
 * 
 */
package com.emisi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import oracle.ord.dicom.OrdDicom;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emisi.dao.DicomJDBC;
import com.emisi.dao.SerieDAOImpl;
import com.emisi.model.Imagen;

/**
 * @author mjaguero
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/spring-services.xml",
		"/spring-hibernate.xml"})
		
		@SuppressWarnings("unchecked")
public class TestServicioSerie extends AbstractJUnit4SpringContextTests {

	
	@org.junit.Test
	public void testQueryByClass() {
		GenericService<Imagen> service = (GenericService<Imagen>) applicationContext
		.getBean("service.serie");
		
		service.findAll();
	}
	
	
	@org.junit.Test
	public void testQueryBySQL() {
		SerieDAOImpl dao = (SerieDAOImpl) applicationContext.getBean("dao.serie");
		
		dao.findAll();
	}
	
}
