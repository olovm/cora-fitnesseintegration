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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParser;
import se.uu.ub.cora.json.parser.org.OrgJsonParser;

public class MetadataLinkFixtureTest {

	MetadataLinkFixture fixture;

	@BeforeMethod
	public void setUp() {
		fixture = new MetadataLinkFixture();
		JsonHolder.setJson(null);
		fixture.setNameInData("someNameInData");
		fixture.setLinkedRecordType("metadataGroup");
		fixture.setLinkedRecordId("someRecordId");
	}

	@Test
	public void testLinkIsNotPresent() {
		// fixture.setJSON("{}");
		assertFalse(fixture.linkIsPresent());
	}

	@Test
	public void testLinkIsPresent() {
		fixture.setLinkedRecordId("recordInfoNewGroup");
		String jsonRecord = "{\"data\":{\"children\":[{\"children\":[{\"name\":\"id\",\"value\":\"metadataGroupNewGroup\"},{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"recordType\"},{\"name\":\"linkedRecordId\",\"value\":\"metadataGroup\"}],\"name\":\"type\"},{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"system\"},{\"name\":\"linkedRecordId\",\"value\":\"cora\"}],\"name\":\"dataDivider\"},{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"systemOneUser\"},{\"name\":\"linkedRecordId\",\"value\":\"12345\"}],\"name\":\"createdBy\"},{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"systemOneUser\"},{\"name\":\"linkedRecordId\",\"value\":\"12345\"}],\"name\":\"updatedBy\"},{\"name\":\"tsCreated\",\"value\":\"2017-10-01 00:00:00.0\"},{\"name\":\"tsUpdated\",\"value\":\"2017-11-01 17:46:48.0\"}],\"name\":\"recordInfo\"},{\"name\":\"nameInData\",\"value\":\"metadata\"},{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"metadataGroupNewGroupText\"}],\"name\":\"textId\"},{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"metadataGroupNewGroupDefText\"}],\"name\":\"defTextId\"},{\"children\":[{\"repeatId\":\"0\",\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"metadataCollectionVariable\"},{\"name\":\"linkedRecordId\",\"value\":\"metadataTypeGroupCollectionVar\"}],\"name\":\"ref\"}],\"name\":\"attributeReferences\"},{\"children\":[{\"repeatId\":\"1\",\"children\":[{\"name\":\"repeatMin\",\"value\":\"1\"},{\"name\":\"repeatMax\",\"value\":\"1\"},{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"metadataGroup\"},{\"name\":\"linkedRecordId\",\"value\":\"recordInfoNewGroup\"}],\"name\":\"ref\"}],\"name\":\"childReference\"},{\"repeatId\":\"2\",\"children\":[{\"name\":\"repeatMin\",\"value\":\"1\"},{\"name\":\"repeatMax\",\"value\":\"1\"},{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"metadataTextVariable\"},{\"name\":\"linkedRecordId\",\"value\":\"nameInDataTextVar\"}],\"name\":\"ref\"}],\"name\":\"childReference\"},{\"repeatId\":\"3\",\"children\":[{\"name\":\"repeatMin\",\"value\":\"0\"},{\"name\":\"repeatMax\",\"value\":\"1\"},{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"metadataRecordLink\"},{\"name\":\"linkedRecordId\",\"value\":\"textIdLink\"}],\"name\":\"ref\"}],\"name\":\"childReference\"}],\"name\":\"childReferences\"}],\"name\":\"metadata\",\"attributes\":{\"type\":\"group\"}},\"actionLinks\":{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\"http://localhost:8080/therest/rest/record/metadataGroup/metadataGroupNewGroup\",\"accept\":\"application/vnd.uub.record+json\"}}}";
		JsonParser jsonParser = new OrgJsonParser();
		JsonHolder.setJson((JsonObject) jsonParser.parseString(jsonRecord));
		assertTrue(fixture.linkIsPresent());
	}
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