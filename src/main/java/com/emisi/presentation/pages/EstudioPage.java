package com.emisi.presentation.pages;

import com.emisi.model.Imagen;
import com.emisi.model.Serie;
import com.emisi.presentation.TemplateIndex;
import com.emisi.service.GenericService;
import com.emisi.util.DicomUtils;
import org.apache.commons.collections.FastHashMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

import java.util.List;
import java.util.Map;

public class EstudioPage extends TemplateIndex {

//	private final Logger logger = LoggerFactory.getLogger(ConsultaPage.class);
	private static final long serialVersionUID = 1L;

	@SpringBean(name = "service.serie")
	private GenericService<Serie> service;

	public EstudioPage(final PageParameters parameters) {
		super(parameters);

		String estudioId = parameters.getString("idEstudio");

		Map<String, Object> parametros = new FastHashMap();
		parametros.put("idEstudio", estudioId);
		List<Serie> series = service.findByNamedQuery("byEstudioId", parametros);

		@SuppressWarnings("unchecked")
		final DataView dataView = new DataView("tablaSeries", new ListDataProvider(
				series)) {

			@Override
			protected void populateItem(Item item) {
				final Serie serie = (Serie) item.getModelObject();

				if (serie == null) {
					item.add(new Label("id", "-"));
					item.add(new Label("descripcion", "N/A"));
//					item.add(new Label("diagnostico", "N/A"));
//					item.add(new Label("imagen", "Imagen no disponible."));
					item.add(new Label("detalle", "-"));
					return;
				}

				// id de la imagen
				item.add(new Label("id", serie.getIdSerie().substring(0, 14).concat("...")));//.substring(0, 10)));
				// id de la imagen
				item.add(new Label("descripcion", serie.getDescripcion()));
				// id de la imagen
//				item.add(new Label("diagnostico", ""));

				//link al detalle de la imagen
				PageParameters pars = new PageParameters();
				pars.add("idSerie", serie.getIdSerie());

				BookmarkablePageLink linkDetalle = new BookmarkablePageLink("detalle", SeriePage.class, pars);
				item.add(linkDetalle);


			}
		};

		dataView.setItemsPerPage(10);

		add(dataView);

		add(new PagingNavigator("navigator", dataView));

	}
	
}
