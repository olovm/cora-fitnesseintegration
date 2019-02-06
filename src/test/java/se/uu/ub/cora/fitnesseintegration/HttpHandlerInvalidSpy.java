/*
 * Copyright 2017 Uppsala University Library
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

package se.uu.ub.cora.fitnesseintegration;

import java.io.InputStream;
import java.net.HttpURLConnection;

import se.uu.ub.cora.httphandler.HttpHandler;

public class HttpHandlerInvalidSpy implements HttpHandler {

	public HttpURLConnection urlConnection;

	public HttpHandlerInvalidSpy(HttpURLConnection urlConnection) {
		this.urlConnection = urlConnection;

	}

	public static HttpHandlerInvalidSpy usingURLConnection(HttpURLConnection urlConnection) {
		return new HttpHandlerInvalidSpy(urlConnection);
	}

	@Override
	public void setRequestMethod(String requestMetod) {

	}

	@Override
	public String getResponseText() {
		return null;
	}

	@Override
	public int getResponseCode() {
		return 400;
	}

	@Override
	public void setOutput(String outputString) {

	}

	@Override
	public void setRequestProperty(String key, String value) {

	}

	@Override
	public String getErrorText() {
		return "bad things happend";
	}

	@Override
	public void setStreamOutput(InputStream stream) {
	}

	@Override
	public String getHeaderField(String name) {
		return null;
	}

}
