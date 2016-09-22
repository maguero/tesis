/**
 * 
 */
package com.emisi.service;

import com.emisi.model.Imagen;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	@Ignore
	public void testQueryByClass() {
		GenericService<Imagen> service = (GenericService<Imagen>) applicationContext
		.getBean("service.serie");
		
		service.findAll();
	}

}
