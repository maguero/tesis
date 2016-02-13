/**
 * 
 */
package com.emisi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import oracle.ord.dicom.OrdDicom;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emisi.dao.DicomJDBC;
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
public class TestServicioGenerico extends AbstractJUnit4SpringContextTests {

	
	@org.junit.Test
	public void testSaveEjemplo() throws SQLException, IOException {
		GenericService<Imagen> service = (GenericService<Imagen>) applicationContext
				.getBean("service.imagen");
		
		DicomJDBC jdbc = new DicomJDBC();
		
		File file = new File("C:\\mj\\emisi\\dicom\\MR\\NCRI\\ASP221\\2.16.124.113543.6003.2724626544.33753.17207.662539691\\000000\\000001.dcm");
		
		Imagen expected = new Imagen();
		expected.setIdImagen("1");
		expected.setDicom((OrdDicom) OrdDicom.getORADataFactory());
		//service.save(expected);
		jdbc.saveImagen(expected, file);

		Imagen actual = service.findById(expected.getIdImagen());
		
		assertEquals("Las entidades no son iguales", expected.getIdImagen(), actual.getIdImagen());
		
	}
	
	@org.junit.Test
	public void testQueryByClass() {
		GenericService<Imagen> service = (GenericService<Imagen>) applicationContext
		.getBean("service.imagen");
		
		service.findAll();
	}
	
	@org.junit.Test
	public void testDeleteEjemplo() {
		GenericService<Imagen> service = (GenericService<Imagen>) applicationContext
				.getBean("service.imagen");
		
		Imagen expected = new Imagen();
		expected.setIdImagen("1");
		//service.save(expected);

		Imagen actual = service.findById(expected.getIdImagen());
		service.delete(actual);
		
		assertNull("No se elimino la entidad", service.findById(expected.getIdImagen()));
		
	}

}
