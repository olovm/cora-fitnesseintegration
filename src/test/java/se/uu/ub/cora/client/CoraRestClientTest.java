/*
 * Copyright 2015, 2016, 2018 Uppsala University Library
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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.fitnesseintegration.DependencyProvider;
import se.uu.ub.cora.fitnesseintegration.HttpHandlerFactorySpy;
import se.uu.ub.cora.fitnesseintegration.HttpHandlerSpy;

public class CoraRestClientTest {
	private HttpHandlerFactorySpy httpHandlerFactorySpy;
	private CoraRestClient coraClient;

	@BeforeMethod
	public void setUp() {
		DependencyProvider.setHttpHandlerFactoryClassName(
				"se.uu.ub.cora.fitnesseintegration.HttpHandlerFactorySpy");
		httpHandlerFactorySpy = (HttpHandlerFactorySpy) DependencyProvider.getFactory();
		String baseUrl = "http://localhost:8080/therest/rest/record/";
		String authToken = "someToken";
		coraClient = CoraRestClientImp.usingHttpHandlerFactoryAndBaseUrlAndAuthToken(
				httpHandlerFactorySpy, baseUrl, authToken);
	}

	@Test
	public void testReadRecordHttpHandlerSetupCorrectly() {
		coraClient.readRecordAsJson("someType", "someId");
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestMetod, "GET");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId");
		assertEquals(httpHandlerSpy.requestProperties.get("authToken"), "someToken");
		assertEquals(httpHandlerSpy.requestProperties.size(), 1);
	}

	@Test
	public void testReadRecordOk() {
		String json = coraClient.readRecordAsJson("someType", "someId");
		assertEquals(json, "Everything ok");
	}

	@Test(expectedExceptions = CoraClientException.class, expectedExceptionsMessageRegExp = ""
			+ "Could not read record of type: someType and id: someId from server using "
			+ "url: http://localhost:8080/therest/rest/record/someType/someId. Returned error was: "
			+ "bad things happened")
	public void testReadRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		coraClient.readRecordAsJson("someType", "someId");
	}

}
