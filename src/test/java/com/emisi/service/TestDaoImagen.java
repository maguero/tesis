/**
 * 
 */
package com.emisi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emisi.dao.ImagenDAOImpl;

/**
 * @author mjaguero
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/spring-services.xml",
		"/spring-hibernate.xml"})
		
		@SuppressWarnings("unchecked")
public class TestDaoImagen extends AbstractJUnit4SpringContextTests {

	
	@org.junit.Test
	@Ignore
	public void testFindAll() {
		ImagenDAOImpl dao = (ImagenDAOImpl) applicationContext
		.getBean("dao.imagen");
		
		//dao.findAll();
	}
	
}
