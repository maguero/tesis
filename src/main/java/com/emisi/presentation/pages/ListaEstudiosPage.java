package com.emisi.presentation.pages;

import com.emisi.model.Estudio;
import com.emisi.presentation.TemplateIndex;
import com.emisi.service.GenericService;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class ListaEstudiosPage extends TemplateIndex {

//	private final Logger logger = LoggerFactory.getLogger(ConsultaPage.class);
	private static final long serialVersionUID = 1L;

	@SpringBean(name = "service.estudio")
	private GenericService<Estudio> service;

	public ListaEstudiosPage(final PageParameters parameters) {
		super(parameters);

		 List<Estudio> estudios = service.findAll();

		@SuppressWarnings("unchecked")
		final DataView dataView = new DataView("tablaEstudios", new ListDataProvider(
                estudios)) {

			@Override
			protected void populateItem(Item item) {
				final Estudio estudio = (Estudio) item.getModelObject();
				
				if (estudio == null) {
					item.add(new Label("id", "-"));
					item.add(new Label("fkPaciente", "N/A"));
					item.add(new Label("descripcion", "N/A"));
					item.add(new Label("detalle", "-"));
					return;
				}
				
                // id de la imagen
				item.add(new Label("id", estudio.getIdEstudio().substring(0, 14).concat("...")));//.substring(0, 10)));
				// id de la imagen
				item.add(new Label("fkPaciente", estudio.getIdPaciente()));
				// id de la imagen
				item.add(new Label("descripcion", estudio.getDescripcion()));
				
				//link al detalle de la imagen
				PageParameters pars = new PageParameters();
				pars.add("idEstudio", estudio.getIdEstudio());
				
				BookmarkablePageLink linkDetalle = new BookmarkablePageLink("detalle", EstudioPage.class, pars);
				item.add(linkDetalle);
				
                
			}
        };
 
        dataView.setItemsPerPage(10);
         
        add(dataView);
 
        add(new PagingNavigator("navigator", dataView));

    }
	
}
