package com.emisi.presentation.pages;

import com.emisi.model.Imagen;
import com.emisi.model.Serie;
import com.emisi.presentation.TemplateIndex;
import com.emisi.service.GenericService;
import com.emisi.util.DicomUtils;
import org.apache.commons.collections.FastHashMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.dcm4che2.data.DicomObject;

import java.util.List;
import java.util.Map;

public class SeriePage extends TemplateIndex {

//	private final Logger logger = LoggerFactory.getLogger(ConsultaPage.class);
	private static final long serialVersionUID = 1L;

	@SpringBean(name = "service.imagen")
	private GenericService<Imagen> service;

	@SpringBean(name = "service.serie")
	private GenericService<Serie> serieService;

	public SeriePage(final PageParameters parameters) {
		super(parameters);

		String serieId = parameters.getString("idSerie");

		final Serie serie = serieService.findById(serieId);

		// tabla de informacion de serie
		add(new Label("serie.id", serie.getIdSerie()));
		add(new Label("serie.descripcion", serie.getDescripcion()));
		add(new Label("serie.modalidad", "----"));
		add(new Label("serie.estudio", serie.getFkEstudio()));

		Map<String, Object> parametros = new FastHashMap();
		parametros.put("idSerie", serieId);
		List<Imagen> imagenes = service.findByNamedQuery("bySerieId", parametros);

		@SuppressWarnings("unchecked")
		final DataView dataView = new DataView("tablaImagen", new ListDataProvider(
				imagenes)) {

			@Override
			protected void populateItem(Item item) {
				final Imagen imagen = (Imagen) item.getModelObject();

				if (imagen == null) {
					item.add(new Label("id", "-"));
					item.add(new Label("imagen", "Imagen no disponible."));
					item.add(new Label("detalle", "-"));
					return;
				}

				// id de la imagen
				item.add(new Label("id", imagen.getIdImagen().substring(0, 9)
						.concat("...")
						.concat(imagen.getIdImagen().substring((imagen.getIdImagen().length() - 9), imagen.getIdImagen().length()))));

				//link al detalle de la imagen
				PageParameters pars = new PageParameters();
				pars.add("id", imagen.getIdImagen());

				BookmarkablePageLink linkDetalle = new BookmarkablePageLink("detalle", ImagenPage.class, pars);

				// imagen
				try {
					DicomObject dcmObj = DicomUtils.getDicomObject(imagen.getDicom());
					final Image imageFromDicom = DicomUtils.getImageFromDicom(dcmObj);
					linkDetalle.add(imageFromDicom);
				} catch (Exception e1) {
					linkDetalle.add(new Label("imagen", "Imagen no disponible."));
					e1.printStackTrace();
				}

				item.add(linkDetalle);

			}
		};

		dataView.setItemsPerPage(10);

		add(dataView);

		add(new PagingNavigator("navigator", dataView));

	}
	
}
