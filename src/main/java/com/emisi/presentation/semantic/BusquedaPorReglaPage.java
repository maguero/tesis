package com.emisi.presentation.semantic;

import com.emisi.dao.ImagenDAOImpl;
import com.emisi.model.Imagen;
import com.emisi.model.Regla;
import com.emisi.presentation.TemplateIndex;
import com.emisi.presentation.pages.ImagenPage;
import com.emisi.service.GenericService;
import com.emisi.util.DicomUtils;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.dcm4che2.data.DicomObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusquedaPorReglaPage extends TemplateIndex {

	private static final long serialVersionUID = 1L;

	private String selectedRule;
	private List<Imagen> imagenes;

	@SpringBean(name = "dao.custom.imagen")
	private ImagenDAOImpl dao;

	public BusquedaPorReglaPage(final PageParameters parameters) {
		super(parameters);

		final List<Regla> reglas = dao.find("reglas", new HashMap());

		final DropDownChoice<Regla> listSites = new DropDownChoice<Regla>(
				"rules", new PropertyModel<Regla>(this, "selectedRule"), reglas);


		final Form form = new Form("form");
		final SubmitLink submit = new SubmitLink("submit", form);
		form.add(listSites);
		add(form);

		final MarkupContainer contenedor = new WebMarkupContainer("contenedor");

		imagenes = new ArrayList<Imagen>();


		ListDataProvider<Imagen> dataImagenes = new ListDataProvider<Imagen>() {
			@Override
			protected List<Imagen> getData() {
				return imagenes;
			}

		};

		@SuppressWarnings("unchecked")
		final DataView dataView = new DataView("tablaImagen", dataImagenes) {

			@Override
			protected void populateItem(Item item) {
				final Imagen imagen = (Imagen) item.getModelObject();

				if (imagen == null) {
					item.add(new Label("id", "-"));
					item.add(new Label("serieId", "-"));
					item.add(new Label("modalidad", "N/A"));
					item.add(new Label("imagen", "Imagen no disponible."));
					return;
				}

				item.add(new Label("id", imagen.getIdImagen().substring(0, 9)
						.concat("...")
						.concat(imagen.getIdImagen().substring((imagen.getIdImagen().length() - 9), imagen.getIdImagen().length()))));
				item.add(new Label("serieId", imagen.getFkSerie()
						.concat("...")
						.concat(imagen.getFkSerie().substring((imagen.getFkSerie().length() - 9), imagen.getFkSerie().length()))));
				item.add(new Label("modalidad", "--"));


				//link al detalle de la imagen
				PageParameters pars = new PageParameters();
				pars.add("id", imagen.getIdImagen());

				BookmarkablePageLink linkDetalle = new BookmarkablePageLink("detalle", ImagenPage.class, pars);

				try {
					DicomObject dcmObj = DicomUtils.getDicomObject(imagen.getDicom());
					linkDetalle.add(DicomUtils.getImageFromDicom(dcmObj));
				} catch (Exception e1) {
					item.add(new Label("imagen", "Imagen no disponible."));
					e1.printStackTrace();
				}

				item.add(linkDetalle);


			}
		};

		dataView.setItemsPerPage(10);
		dataView.setOutputMarkupId(true);
		contenedor.add(dataView);
		final PagingNavigator navigator = new PagingNavigator("navigator", dataView);
		navigator.setOutputMarkupId(true);
		contenedor.add(navigator);
		contenedor.setOutputMarkupId(true);
		add(contenedor);

		listSites.add(new AjaxFormComponentUpdatingBehavior("onChange")
		{
			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				final int punto = selectedRule.indexOf('.');
				final String regla = selectedRule.substring(0, punto).concat(selectedRule.substring(punto + 1, punto + 2).toUpperCase()).concat(selectedRule.substring(punto + 2));
				imagenes = dao.findByRule(regla);
				target.addComponent(contenedor);
			}
		});
	}

	private List<Imagen> buscarPorRegla(final String nombreRegla) {
		return dao.findByRule(nombreRegla);
	}

	public String getSelectedRule() {
		return selectedRule;
	}

	public void setSelectedRule(String selectedRule) {
		this.selectedRule = selectedRule.toLowerCase();
	}
}
