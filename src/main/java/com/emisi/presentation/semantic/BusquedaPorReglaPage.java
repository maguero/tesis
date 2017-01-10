package com.emisi.presentation.semantic;

import com.emisi.dao.ImagenDAOImpl;
import com.emisi.model.Imagen;
import com.emisi.model.Regla;
import com.emisi.presentation.TemplateIndex;
import com.emisi.service.GenericService;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;
import java.util.Map;

public class BusquedaPorReglaPage extends TemplateIndex {

	private static final long serialVersionUID = 1L;

	@SpringBean(name = "dao.custom.imagen")
	private ImagenDAOImpl dao;

	public BusquedaPorReglaPage(final PageParameters parameters) {
		super(parameters);

		final List<Regla> reglas = dao.getRules();

		@SuppressWarnings("unchecked")
		final ListView comboBox = new ListView("rules", reglas) {
			@Override
			protected void populateItem(ListItem listItem) {
				final Regla regla = (Regla) listItem.getModelObject();
				listItem.add(new Label("id", regla.getId()));
				listItem.add(new Label("nombre", regla.getNombre()));
			}
		};

//		add(comboBox);

		final Form form = new Form("form");
		final SubmitLink submit = new SubmitLink("submit", form);
		form.add(comboBox);
		// TODO add form??
		add(submit);
	}

	private List<Imagen> buscarPorRegla(final String nombreRegla) {
		return dao.findByRule(nombreRegla);
	}
	
}
