package com.emisi.presentation.pages;

import com.emisi.dao.ImagenDAOImpl;
import com.emisi.model.Estudio;
import com.emisi.model.IdNombre;
import com.emisi.model.Serie;
import com.emisi.presentation.TemplateIndex;
import com.emisi.service.GenericService;
import com.google.common.base.Joiner;
import org.apache.commons.collections.FastHashMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;
import java.util.Map;

public class EstudioPage extends TemplateIndex {

//	private final Logger logger = LoggerFactory.getLogger(ConsultaPage.class);
	private static final long serialVersionUID = 1L;

	@SpringBean(name = "service.serie")
	private GenericService<Serie> service;

	@SpringBean(name = "service.estudio")
	private GenericService<Estudio> estudioService;

	@SpringBean(name = "dao.custom.imagen")
	private ImagenDAOImpl utilDao;

	public EstudioPage(final PageParameters parameters) {
		super(parameters);

		String estudioId = parameters.getString("idEstudio");

		final Estudio estudio = estudioService.findById(estudioId);

		// tabla de informacion de serie
		add(new Label("estudio.id", estudio.getIdEstudio()));
		add(new Label("estudio.descripcion", estudio.getDescripcion()));
		add(new Label("estudio.paciente", estudio.getIdPaciente()));
		add(new Label("estudio.diagnosticos", getDiagnosticos(estudioId)));
		add(new Label("estudio.sintomas", getSintomas(estudioId)));

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
					item.add(new Label("detalle", "-"));
					return;
				}

				item.add(new Label("descripcion", serie.getDescripcion()));

				//link al detalle de la imagen
				PageParameters pars = new PageParameters();
				pars.add("idSerie", serie.getIdSerie());

				BookmarkablePageLink linkDetalle = new BookmarkablePageLink("detalle", SeriePage.class, pars);
				linkDetalle.add(new Label("id", serie.getIdSerie().substring(0, 9)
						.concat("...")
						.concat(serie.getIdSerie().substring((serie.getIdSerie().length() - 9), serie.getIdSerie().length()))
				));
				item.add(linkDetalle);


			}
		};

		dataView.setItemsPerPage(10);

		add(dataView);

		add(new PagingNavigator("navigator", dataView));

	}

	private String getSintomas(final String estudioId) {
		final List<IdNombre> sintomas = utilDao.getSintomasByStudy(estudioId);
		return Joiner.on("  ").join(sintomas);
	}

	private String getDiagnosticos(final String estudioId) {
		final List<IdNombre> diagnosticos = utilDao.getDiagnosticosByStudy(estudioId);
		return Joiner.on("  ").join(diagnosticos);
	}

}
