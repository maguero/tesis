package com.emisi.presentation.semantic;

import com.emisi.dao.ImagenDAOImpl;
import com.emisi.model.Imagen;
import com.emisi.presentation.TemplateIndex;
import com.emisi.presentation.pages.ImagenPage;
import com.emisi.util.DicomUtils;
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

public class SimilarPage extends TemplateIndex {

	private static final long serialVersionUID = 1L;

	@SpringBean(name = "dao.custom.imagen")
	private ImagenDAOImpl dao;

	public SimilarPage(final PageParameters parameters) {
		super(parameters);

		String id = parameters.getString("idImagen");

		List<Imagen> imagenes = dao.similar(id);

		@SuppressWarnings("unchecked")
		final DataView dataView = new DataView("tablaImagen", new ListDataProvider(
				imagenes)) {

			@Override
			protected void populateItem(Item item) {
				final Imagen imagen = (Imagen) item.getModelObject();

				if (imagen == null) {
					item.add(new Label("id", "-"));
//					item.add(new Label("modalidad", "N/A"));
//					item.add(new Label("diagnostico", "N/A"));
					item.add(new Label("imagen", "Imagen no disponible."));
					item.add(new Label("detalle", "-"));
					return;
				}

				// id de la imagen
				item.add(new Label("id", imagen.getIdImagen().substring(0, 14).concat("...")));//.substring(0, 10)));
				// id de la imagen
//				item.add(new Label("modalidad", ""));
				// id de la imagen
//				item.add(new Label("diagnostico", ""));

				// imagen
				DicomInputStream din = null;
				try {
					DicomObject dcmObj = DicomUtils.getDicomObject(imagen.getDicom());
					item.add(DicomUtils.getImageFromDicom(dcmObj));
				} catch (Exception e1) {
					item.add(new Label("imagen", "Imagen no disponible."));
					e1.printStackTrace();
				} finally {
					try {
						din.close();
					} catch (Exception ignore) {
					}
				}


				//link al detalle de la imagen
				PageParameters pars = new PageParameters();
				pars.add("id", imagen.getIdImagen());

				BookmarkablePageLink linkDetalle = new BookmarkablePageLink("detalle", ImagenPage.class, pars);
				item.add(linkDetalle);


			}
		};

		dataView.setItemsPerPage(10);

		add(dataView);

		add(new PagingNavigator("navigator", dataView));

	}

}
