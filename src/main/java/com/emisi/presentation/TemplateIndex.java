package com.emisi.presentation;

import com.emisi.presentation.pages.ListaEstudiosPage;
import com.emisi.presentation.semantic.BusquedaPorReglaPage;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

/**
 * Esta clase tiene la responsabilidad de contener todo el contenido comun.
 * 
 * @author maguero
 */
public abstract class TemplateIndex extends WebPage {

	private static final long serialVersionUID = -3116781853798171244L;

	public TemplateIndex(final PageParameters parameters) {
		this.iniciar(parameters);
	}
	
	public TemplateIndex() {
		// TODO Auto-generated constructor stub
	}

	private void iniciar(final PageParameters parameters) {
		
		BookmarkablePageLink linkHome = new BookmarkablePageLink("inicio", IndexPage.class);
		add(linkHome);
		
		BookmarkablePageLink linkCarga = new BookmarkablePageLink("tesis", IndexPage.class);
		add(linkCarga);
		
		BookmarkablePageLink linkListarImagenes = new BookmarkablePageLink("listar", ListaEstudiosPage.class);
		add(linkListarImagenes);

		BookmarkablePageLink linkBusquedaPorRegla = new BookmarkablePageLink("busquedaPorRegla", BusquedaPorReglaPage.class);
		add(linkBusquedaPorRegla);

		BookmarkablePageLink linkBusquedaSemantica = new BookmarkablePageLink("busquedaSemantica", BusquedaPorReglaPage.class);
		add(linkBusquedaSemantica);
		
//		BookmarkablePageLink linkAutorizacion = new BookmarkablePageLink("carga", CargaImagenesPage.class);
//		add(linkAutorizacion);
		
		/*		
		BookmarkablePageLink linkAyuda = new BookmarkablePageLink("ayuda", AyudaPage.class);
		add(linkAyuda);
		*/
	}
}