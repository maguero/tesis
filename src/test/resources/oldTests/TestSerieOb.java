/**
 * 
 */
package com.emisi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emisi.model.Imagen;
import com.emisi.model.Serie;

/**
 * @author mjaguero
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/spring-services.xml",
		"/spring-hibernate.xml"})
		
		@SuppressWarnings("unchecked")
public class TestSerieOb extends AbstractJUnit4SpringContextTests {

	
	@org.junit.Test
	public void testSaveEjemplo() throws SQLException, IOException {
		GenericService<Serie> service = (GenericService<Serie>) applicationContext
				.getBean("service.serie");
		
		Serie serie = new Serie();
		serie.setId("AAAAAAAAAA");
		Imagen imagen = new Imagen();
		imagen.setIdImagen("99");
		serie.setImagen(imagen);
		
		service.save(serie);

		Serie actual = service.findById(serie.getId());
		
		assertEquals("Las entidades no son iguales", serie.getId(), actual.getId());
		
	}
	
	@org.junit.Test
	public void testQueryByClass() {
		GenericService<Serie> service = (GenericService<Serie>) applicationContext
		.getBean("service.serie");
		
		service.findAll();
	}
	
	@org.junit.Test
	public void testDeleteEjemplo() {
		GenericService<Serie> service = (GenericService<Serie>) applicationContext
				.getBean("service.serie");
		
		Serie serie = new Serie();
		serie.setId("AAAAAAAAAA");

		Serie actual = service.findById(serie.getId());
		service.delete(actual);
		
		assertNull("No se elimino la entidad", service.findById(serie.getId()));
		
	}

}
