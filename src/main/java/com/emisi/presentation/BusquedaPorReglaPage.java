package com.emisi.presentation;

import com.emisi.model.Imagen;
import com.emisi.service.GenericService;
import org.apache.wicket.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class BusquedaPorReglaPage extends TemplateIndex {

	private static final long serialVersionUID = 1L;

	@SpringBean(name = "service.imagen")
	private GenericService<Imagen> imagenService;

	public BusquedaPorReglaPage(final PageParameters parameters) {
		super(parameters);



    }
	
}
