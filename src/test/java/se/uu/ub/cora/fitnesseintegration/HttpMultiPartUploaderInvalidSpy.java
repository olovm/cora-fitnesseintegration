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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import se.uu.ub.cora.httphandler.HttpMultiPartUploader;

public class HttpMultiPartUploaderInvalidSpy implements HttpMultiPartUploader {

	public HttpURLConnection urlConnection;

	public HttpMultiPartUploaderInvalidSpy(HttpURLConnection urlConnection) {
		this.urlConnection = urlConnection;
	}

	public static HttpMultiPartUploaderInvalidSpy usingURLConnection(
			HttpURLConnection urlConnection) {
		return new HttpMultiPartUploaderInvalidSpy(urlConnection);
	}

	@Override
	public String getResponseText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResponseCode() {
		return 400;
	}

	@Override
	public String getErrorText() {
		return "bad things happend";
	}

	@Override
	public void addFormField(String name, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFilePart(String fieldName, String fileName, InputStream stream)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addHeaderField(String name, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done() throws IOException {
		// TODO Auto-generated method stub

	}

}
