package com.emisi;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.emisi.presentation.IndexPage;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see com.emisi.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return IndexPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		// add your configuration here
		getDebugSettings().setAjaxDebugModeEnabled(false);
		addComponentInstantiationListener(new SpringComponentInjector(this));
		/*
		getApplicationSettings().setPageExpiredErrorPage(IndexPage.class);
		getApplicationSettings().setAccessDeniedPage(IndexPage.class);
		getApplicationSettings().setInternalErrorPage(IndexPage.class);
		*/
		
	}
}
