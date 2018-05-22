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
import java.lang.reflect.InvocationTargetException;

import se.uu.ub.cora.client.CoraRestClientFactory;
import se.uu.ub.cora.httphandler.HttpHandlerFactory;

public final class DependencyProvider {

	private static HttpHandlerFactory httpHandlerFactory;
	private static CoraRestClientFactory coraRestClientFactory;

	public DependencyProvider() {
		// needs a public constructor for fitnesse to work
		super();
	}

	public static synchronized void setHttpHandlerFactoryClassName(
			String httpHandlerFactoryClassName) {
		try {
			tryToCreateHttpHandler(httpHandlerFactoryClassName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void tryToCreateHttpHandler(String httpHandlerFactoryClassName)
			throws NoSuchMethodException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		Constructor<?> constructor = Class.forName(httpHandlerFactoryClassName).getConstructor();
		httpHandlerFactory = (HttpHandlerFactory) constructor.newInstance();
	}

	public static HttpHandlerFactory getFactory() {
		return httpHandlerFactory;
	}

	public static CoraRestClientFactory getRestClientFactory() {
		return coraRestClientFactory;
	}

	public static void setCoraRestClientFactoryClassName(String coraRestClientFactoryClassName) {
		try {
			tryToCreateCoraRestClientFactory(coraRestClientFactoryClassName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void tryToCreateCoraRestClientFactory(String coraRestClientFactoryClassName)
			throws NoSuchMethodException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		Constructor<?> constructor = Class.forName(coraRestClientFactoryClassName)
				.getConstructor();
		coraRestClientFactory = (CoraRestClientFactory) constructor.newInstance();
	}

}
