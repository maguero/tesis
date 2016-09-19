package com.emisi;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.emisi.presentation.IndexPage;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	@Ignore
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(IndexPage.class);

		//assert rendered page class
		tester.assertRenderedPage(IndexPage.class);
	}
}
