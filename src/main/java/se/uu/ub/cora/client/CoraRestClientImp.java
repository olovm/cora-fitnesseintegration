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

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import se.uu.ub.cora.httphandler.HttpHandler;
import se.uu.ub.cora.httphandler.HttpHandlerFactory;

public class CoraRestClientImp implements CoraRestClient {
	private HttpHandlerFactory httpHandlerFactory;
	private String baseUrl;
	private String authToken;

	public static CoraRestClientImp usingHttpHandlerFactoryAndBaseUrlAndAuthToken(
			HttpHandlerFactory httpHandlerFactory, String baseUrl, String authToken) {
		return new CoraRestClientImp(httpHandlerFactory, baseUrl, authToken);
	}

	private CoraRestClientImp(HttpHandlerFactory httpHandlerFactory, String baseUrl,
			String authToken) {
		this.httpHandlerFactory = httpHandlerFactory;
		this.baseUrl = baseUrl;
		this.authToken = authToken;
	}

	@Override
	public String readRecordAsJson(String recordType, String recordId) {
		String url = baseUrl + recordType + "/" + recordId;
		HttpHandler httpHandler = createHttpHandlerWithAuthTokenAndUrl(url);
		httpHandler.setRequestMethod("GET");

		Status statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());
		if (statusType.equals(Response.Status.OK)) {
			return httpHandler.getResponseText();
		}
		throw new CoraClientException("Could not read record of type: " + recordType + " and id: "
				+ recordId + " from server using url: " + url + ". Returned error was: "
				+ httpHandler.getErrorText());
	}

	private HttpHandler createHttpHandlerWithAuthTokenAndUrl(String url) {
		HttpHandler httpHandler = httpHandlerFactory.factor(url);
		httpHandler.setRequestProperty("authToken", authToken);
		return httpHandler;
	}

	HttpHandlerFactory getHttpHandlerFactory() {
		// needed for test
		return httpHandlerFactory;
	}

	String getBaseUrl() {
		// needed for test
		return baseUrl;
	}

	String getAuthToken() {
		// needed for test
		return authToken;
	}

}
