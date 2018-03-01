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

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataRecord;

public class MetadataLinkFixtureTest {

	MetadataLinkFixture fixture;

	@BeforeMethod
	public void setUp() {
		fixture = new MetadataLinkFixture();

		ClientDataGroup topLevelDataGroup = createTopLevelDataGroup();

		ClientDataRecord record = ClientDataRecord.withClientDataGroup(topLevelDataGroup);
		RecordHolder.setRecord(record);

	}

	private ClientDataGroup createTopLevelDataGroup() {
		ClientDataGroup topLevelDataGroup = ClientDataGroup.withNameInData("metadata");
		ClientDataGroup childReferences = ClientDataGroup.withNameInData("childReferences");
		ClientDataGroup childReference = createChildReferenceWithRepeatIdRecordTypeAndRecordId("0",
				"metadataGroup", "someRecordId");
		childReferences.addChild(childReference);
		topLevelDataGroup.addChild(childReferences);
		return topLevelDataGroup;
	}

	private ClientDataGroup createChildReferenceWithRepeatIdRecordTypeAndRecordId(String repeatId,
			String linkedRecordType, String linkedRecordId) {
		ClientDataGroup childReference = ClientDataGroup.withNameInData("childReference");
		childReference.setRepeatId(repeatId);
		ClientDataGroup ref = ClientDataGroup.withNameInData("ref");
		ref.addChild(ClientDataAtomic.withNameInDataAndValue("linkedRecordType", linkedRecordType));
		ref.addChild(ClientDataAtomic.withNameInDataAndValue("linkedRecordId", linkedRecordId));
		childReference.addChild(ref);
		return childReference;
	}

	@Test
	public void testLinkIsNotPresentRecordIsNull() {
		RecordHolder.setRecord(null);
		fixture.setLinkedRecordType("metadataGroup");
		fixture.setLinkedRecordId("someRecordId");
		assertFalse(fixture.linkIsPresent());
	}

	@Test
	public void testLinkIsNotPresentRecordContainsNoDataGroup() {
		ClientDataRecord record = ClientDataRecord.withClientDataGroup(null);
		RecordHolder.setRecord(record);
		fixture.setLinkedRecordType("metadataGroup");
		fixture.setLinkedRecordId("someRecordId");
		assertFalse(fixture.linkIsPresent());
	}

	@Test
	public void testLinkIsNotPresent() {
		ClientDataGroup topLevelDataGroup = ClientDataGroup.withNameInData("metadata");
		ClientDataRecord record = ClientDataRecord.withClientDataGroup(topLevelDataGroup);
		RecordHolder.setRecord(record);
		fixture.setLinkedRecordType("metadataGroup");
		fixture.setLinkedRecordId("someRecordId");
		assertFalse(fixture.linkIsPresent());
	}

	@Test
	public void testLinkWrongLinkedRecordId() {
		fixture.setLinkedRecordType("metadataGroup");
		fixture.setLinkedRecordId("NOTSomeRecordId");
		assertFalse(fixture.linkIsPresent());
	}

	@Test
	public void testLinkWrongLinkedRecordType() {
		fixture.setLinkedRecordType("NOTMetadataGroup");
		fixture.setLinkedRecordId("someRecordId");
		assertFalse(fixture.linkIsPresent());
	}

	@Test
	public void testLinkIsPresent() {
		fixture.setLinkedRecordType("metadataGroup");
		fixture.setLinkedRecordId("someRecordId");
		assertTrue(fixture.linkIsPresent());
	}

	@Test
	public void testLinkIsPresentAsSecondChild() {
		ClientDataRecord record = RecordHolder.getRecord();
		ClientDataGroup clientDataGroup = record.getClientDataGroup();
		ClientDataGroup childReferences = clientDataGroup
				.getFirstGroupWithNameInData("childReferences");
		ClientDataGroup childReference = createChildReferenceWithRepeatIdRecordTypeAndRecordId("1",
				"metadataGroup", "someOtherRecordId");
		childReferences.addChild(childReference);

		fixture.setLinkedRecordType("metadataGroup");
		fixture.setLinkedRecordId("someOtherRecordId");
		assertTrue(fixture.linkIsPresent());
	}
}
