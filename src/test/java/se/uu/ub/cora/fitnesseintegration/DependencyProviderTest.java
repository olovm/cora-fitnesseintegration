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

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import se.uu.ub.cora.client.CoraRestClientFactory;
import se.uu.ub.cora.client.CoraRestClientFactoryImp;
import se.uu.ub.cora.client.CoraRestClientFactorySpy;
import se.uu.ub.cora.httphandler.HttpHandlerFactory;
import se.uu.ub.cora.httphandler.HttpHandlerFactoryImp;

public class DependencyProviderTest {
	@Test
	public void testConstructor() {
		DependencyProvider dependencyProvider = new DependencyProvider();
		assertTrue(dependencyProvider instanceof DependencyProvider);
	}

	@Test
	public void testFactorHttpHandler() {
		DependencyProvider
				.setHttpHandlerFactoryClassName("se.uu.ub.cora.httphandler.HttpHandlerFactoryImp");
		HttpHandlerFactory factored = DependencyProvider.getFactory();
		assertTrue(factored instanceof HttpHandlerFactoryImp);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testFactorHttpHandlerNonExistingClassName() {
		DependencyProvider.setHttpHandlerFactoryClassName("se.uu.ub.cora.fitnesse.DoesNotExistImp");
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testFactorHttpHandlerClassNameNotSet() {
		DependencyProvider.setHttpHandlerFactoryClassName(null);
	}

	@Test
	public void testGetRestClientFactory() throws Exception {
		DependencyProvider
				.setCoraRestClientFactoryClassName("se.uu.ub.cora.client.CoraRestClientFactoryImp");
		CoraRestClientFactory clientFactory = DependencyProvider.getRestClientFactory();
		assertTrue(clientFactory instanceof CoraRestClientFactoryImp);
	}

	@Test
	public void testGetRestClientFactorySpy() throws Exception {
		DependencyProvider
				.setCoraRestClientFactoryClassName("se.uu.ub.cora.client.CoraRestClientFactorySpy");
		CoraRestClientFactory clientFactory = DependencyProvider.getRestClientFactory();
		assertTrue(clientFactory instanceof CoraRestClientFactorySpy);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testFactorRestClientClassNameNotSet() {
		DependencyProvider.setCoraRestClientFactoryClassName(null);
	}
}
