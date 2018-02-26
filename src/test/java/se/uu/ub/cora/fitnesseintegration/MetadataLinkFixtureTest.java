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
package se.uu.ub.cora.fitnesseintegration;

import static org.junit.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MetadataLinkFixtureTest {

	MetadataLinkFixture fixture;

	@BeforeMethod
	public void setUp() {
		fixture = new MetadataLinkFixture();
		fixture.setNameInData("someNameInData");
		fixture.setLinkedRecordType("someRecordType");
		fixture.setLinkedRecordId("someRecordId");
	}

	@Test
	public void testLinkIsNotPresent() {
		fixture.setJSon("{}");
		assertFalse(fixture.linkIsPresent());
	}

	// @Test
	// public void testLinkIsPresent() {
	// fixture.setJSon(
	// "{\"record\":{\"data\":{\"children\":[{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"someRecordType\"},{\"name\":\"linkedRecordId\",\"value\":\"someRecordId\"}],\"name\":\"someNameInData\"}],\"name\":\"recordType\"}}}");
	// assertTrue(fixture.linkIsPresent());
	// }
}

// {
// "record": {
// "data": {
// "children": [
// {
// "children": [
// {
// "name": "linkedRecordType",
// "value": "someRecordType"
// },
// {
// "name": "linkedRecordId",
// "value": "someRecordId"
// }
// ],
// "actionLinks": {
// "read": {
// "requestMethod": "GET",
// "rel": "read",
// "url": "http://localhost:8080/therest/rest/record/metadataGroup/placeGroup",
// "accept": "application/vnd.uub.record+json"
// }
// },
// "name": "someNameInData"
// }
// ],
// "name": "recordType"
// }
// }
// }