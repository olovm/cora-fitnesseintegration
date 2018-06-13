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

import java.util.ArrayList;
import java.util.List;

import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataRecord;

public class MetadataLinkFixture {

	protected String linkedRecordType;
	protected String linkedRecordId;
	private List<ClientDataGroup> childReferenceList = new ArrayList<>();
	private ClientDataGroup matchingChildReference;

	public void setLinkedRecordType(String linkedRecordType) {
		this.linkedRecordType = linkedRecordType;
		tryToSetMatchingChildReference();
	}

	public void setLinkedRecordId(String linkedRecordId) {
		this.linkedRecordId = linkedRecordId;
		tryToSetMatchingChildReference();
	}

	private void tryToSetMatchingChildReference() {
		if (linkedRecordType != null && linkedRecordId != null) {
			resetData();
			tryToSetChildReferenceList();
			setMatchingChildReference();
		}
	}

	private void resetData() {
		matchingChildReference = null;
	}

	private void tryToSetChildReferenceList() {
		ClientDataRecord record = RecordHolder.getRecord();
		ClientDataGroup topLevelDataGroup;
		if (null != record && record.getClientDataGroup() != null) {
			topLevelDataGroup = record.getClientDataGroup();
			setChildReferenceList(topLevelDataGroup);
		}
	}

	private void setChildReferenceList(ClientDataGroup topLevelDataGroup) {
		if (childReferencesExists(topLevelDataGroup)) {
			ClientDataGroup childReferences = topLevelDataGroup
					.getFirstGroupWithNameInData("childReferences");
			childReferenceList = childReferences.getAllGroupsWithNameInData("childReference");
		}
	}

	private boolean childReferencesExists(ClientDataGroup topLevelDataGroup) {
		return topLevelDataGroup.containsChildWithNameInData("childReferences");
	}

	private void setMatchingChildReference() {
		for (ClientDataGroup childReference : childReferenceList) {
			setChildReferenceIfMatchingTypeAndId(childReference);
		}
	}

	private void setChildReferenceIfMatchingTypeAndId(ClientDataGroup childReference) {
		if (childReferenceMatchesTypeAndId(childReference)) {
			matchingChildReference = childReference;
		}
	}

	protected boolean childReferenceMatchesTypeAndId(ClientDataGroup childReference) {
		String childLinkedRecordType = extractValueFromReferenceByNameInData(childReference,
				"linkedRecordType");
		String childLinkedRecordId = extractValueFromReferenceByNameInData(childReference,
				"linkedRecordId");
		return childLinkedRecordId.equals(linkedRecordId)
				&& childLinkedRecordType.equals(linkedRecordType);
	}

	protected String extractValueFromReferenceByNameInData(ClientDataGroup childReference,
			String childNameInData) {
		ClientDataGroup ref = childReference.getFirstGroupWithNameInData("ref");
		return ref.getFirstAtomicValueWithNameInData(childNameInData);
	}

	public String getRepeatMin() {
		return getAtomicValueByNameInDataFromMatchingChild("repeatMin");
	}

	private String getAtomicValueByNameInDataFromMatchingChild(String childNameInData) {
		if (null == matchingChildReference) {
			return "not found";
		}
		return matchingChildReference.getFirstAtomicValueWithNameInData(childNameInData);
	}

	public String getRepeatMax() {
		return getAtomicValueByNameInDataFromMatchingChild("repeatMax");
	}

}
