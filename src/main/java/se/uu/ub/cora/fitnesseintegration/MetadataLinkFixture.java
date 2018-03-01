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

import java.util.List;

import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataRecord;

public class MetadataLinkFixture {

	private String nameInData;
	private String linkedRecordType;
	private String linkedRecordId;

	public void setNameInData(String nameInData) {
		this.nameInData = nameInData;

	}

	public void setLinkedRecordType(String linkedRecordType) {
		this.linkedRecordType = linkedRecordType;

	}

	public void setLinkedRecordId(String linkedRecordId) {
		this.linkedRecordId = linkedRecordId;

	}

	public boolean linkIsPresent() {
		ClientDataRecord record = RecordHolder.getRecord();
		ClientDataGroup topLevelDataGroup;
		if (null == record || record.getClientDataGroup() == null) {
			return false;
		} else {
			topLevelDataGroup = record.getClientDataGroup();
			if (!topLevelDataGroup.containsChildWithNameInData("childReferences")) {
				return false;
			}
		}
		return linkIsPresentInChildReferences(topLevelDataGroup);

	}

	private boolean linkIsPresentInChildReferences(ClientDataGroup topLevelDataGroup) {
		List<ClientDataGroup> childReferenceList = getChildReferenceList(topLevelDataGroup);
		for (ClientDataGroup childReference : childReferenceList) {
			String childLinkedRecordType = extractValueFromReferenceByNameInData(childReference,
					"linkedRecordType");
			String childLinkedRecordId = extractValueFromReferenceByNameInData(childReference,
					"linkedRecordId");

			if (childLinkedRecordId.equals(linkedRecordId)
					&& childLinkedRecordType.equals(linkedRecordType)) {
				return true;
			}
		}
		return false;
	}

	private String extractValueFromReferenceByNameInData(ClientDataGroup childReference,
			String childNameInData) {
		ClientDataGroup ref = childReference.getFirstGroupWithNameInData("ref");
		String childLinkedRecordType = ref.getFirstAtomicValueWithNameInData(childNameInData);
		return childLinkedRecordType;
	}

	private List<ClientDataGroup> getChildReferenceList(ClientDataGroup topLevelDataGroup) {
		ClientDataGroup childReferences = topLevelDataGroup
				.getFirstGroupWithNameInData("childReferences");
		return childReferences.getAllGroupsWithNameInData("childReference");
	}

}
