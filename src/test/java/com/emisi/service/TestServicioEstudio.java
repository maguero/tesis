/**
 * 
 */
package com.emisi.service;

import java.util.List;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emisi.model.Estudio;
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
public class TestServicioEstudio extends AbstractJUnit4SpringContextTests {

	
	@org.junit.Test
	@Ignore
	public void testQueryByClass() {
		GenericService<Estudio> service = (GenericService<Estudio>) applicationContext
		.getBean("service.estudio");
		
		List<Estudio> all = service.findAll();
		
		
		for (Serie serie : all.get(0).getSeries()) {
			System.out.println(serie.getIdSerie() + " - " + serie.getDescripcion());
		}
		
		System.out.println(all);
	}
	
	
}
