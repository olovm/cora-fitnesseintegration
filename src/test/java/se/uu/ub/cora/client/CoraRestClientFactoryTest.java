/*
 * Copyright 2018 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.client;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.httphandler.HttpHandlerFactory;
import se.uu.ub.cora.httphandler.HttpHandlerFactoryImp;

public class CoraRestClientFactoryTest {

	private CoraRestClientFactory factory;
	private String baseUrl = "";
	private String authToken = "";

	@BeforeMethod
	public void beforeMethod() {
		factory = new CoraRestClientFactoryImp();
	}

	@Test
	public void testFactor() throws Exception {
		CoraRestClient coraRestClient = factory.factorUsingUrlAndAuthToken(baseUrl, authToken);
		assertTrue(coraRestClient instanceof CoraRestClientImp);
	}

	@Test
	public void testFactorAddedDependenciesIsOk() throws Exception {
		CoraRestClientImp coraRestClient = (CoraRestClientImp) factory
				.factorUsingUrlAndAuthToken(baseUrl, authToken);
		HttpHandlerFactory handlerFactory = coraRestClient.getHttpHandlerFactory();
		assertTrue(handlerFactory instanceof HttpHandlerFactoryImp);
	}

	@Test
	public void testInputsSentOnToClient() throws Exception {
		CoraRestClientImp coraRestClient = (CoraRestClientImp) factory
				.factorUsingUrlAndAuthToken("someBaseUrl", "someAuthToken");
		assertEquals(coraRestClient.getBaseUrl(), "someBaseUrl");
		assertEquals(coraRestClient.getAuthToken(), "someAuthToken");
	}

	@Test
	public void testInputsSentOnToClientForSure() throws Exception {
		CoraRestClientImp coraRestClient = (CoraRestClientImp) factory
				.factorUsingUrlAndAuthToken("someBaseUrl2", "someAuthToken2");
		assertEquals(coraRestClient.getBaseUrl(), "someBaseUrl2");
		assertEquals(coraRestClient.getAuthToken(), "someAuthToken2");
	}
}
