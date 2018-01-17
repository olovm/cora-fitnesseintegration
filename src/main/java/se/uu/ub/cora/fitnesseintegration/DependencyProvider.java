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

import java.lang.reflect.Constructor;

import se.uu.ub.cora.httphandler.HttpHandlerFactory;

public final class DependencyProvider {

	private static HttpHandlerFactory httpHandlerFactory;

	public DependencyProvider() {
		// needs a public constructor for fitnesse to work
		super();
	}

	public static synchronized void setHttpHandlerFactoryClassName(
			String httpHandlerFactoryClassName) {
		Constructor<?> constructor;
		try {
			constructor = Class.forName(httpHandlerFactoryClassName).getConstructor();
			httpHandlerFactory = (HttpHandlerFactory) constructor.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static HttpHandlerFactory getFactory() {
		return httpHandlerFactory;
	}

}
