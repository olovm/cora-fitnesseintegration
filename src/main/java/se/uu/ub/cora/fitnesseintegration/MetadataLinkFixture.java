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
import se.uu.ub.cora.httphandler.HttpHandler;
import se.uu.ub.cora.httphandler.HttpHandlerFactory;

public class MetadataLinkFixture {

	protected String linkedRecordType;
	protected String linkedRecordId;
	private List<ClientDataGroup> childReferenceList = new ArrayList<>();
	private ClientDataGroup matchingChildReference;
	private HttpHandlerFactory httpHandlerFactory;
	private String baseUrl = SystemUrl.getUrl() + "rest/record/";
	private String authToken;

	public MetadataLinkFixture() {
		httpHandlerFactory = DependencyProvider.getHttpHandlerFactory();
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public void setLinkedRecordType(String linkedRecordType) {
		this.linkedRecordType = linkedRecordType;
		tryToSetMatchingChildReference();
	}

	public void setLinkedRecordId(String linkedRecordId) {
		this.linkedRecordId = linkedRecordId;
		tryToSetMatchingChildReference();
	}

	private void tryToSetMatchingChildReference() {
		if (linkedRecordTypeAndRecordIdExist()) {
			resetData();
			possiblySetChildReferenceList();
			setMatchingChildReference();
		}
	}

	private boolean linkedRecordTypeAndRecordIdExist() {
		return linkedRecordType != null && linkedRecordId != null;
	}

	private void resetData() {
		matchingChildReference = null;
	}

	private void possiblySetChildReferenceList() {
		ClientDataRecord record = RecordHolder.getRecord();
		if (recordContainsDataGroup(record)) {
			ClientDataGroup topLevelDataGroup = record.getClientDataGroup();
			setChildReferenceList(topLevelDataGroup);
		}
	}

	private boolean recordContainsDataGroup(ClientDataRecord record) {
		return null != record && record.getClientDataGroup() != null;
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
		String childLinkedRecordType = extractValueFromReferenceUsingNameInData(childReference,
				"linkedRecordType");
		String childLinkedRecordId = extractValueFromReferenceUsingNameInData(childReference,
				"linkedRecordId");

		if (childReferenceMatchesTypeAndId(childLinkedRecordType, childLinkedRecordId)) {
			matchingChildReference = childReference;
			setUpHttpHandlerForReadingChildReference(childLinkedRecordType, childLinkedRecordId);
		}
	}

	private HttpHandler setUpHttpHandlerForReadingChildReference(String childLinkedRecordType,
			String childLinkedRecordId) {
		String url = baseUrl + childLinkedRecordType + "/" + childLinkedRecordId;
		HttpHandler httpHandler = httpHandlerFactory.factor(url);
		httpHandler.setRequestMethod("GET");
		httpHandler.setRequestProperty("authToken", authToken);
		return httpHandler;
	}

	protected boolean childReferenceMatchesTypeAndId(String childLinkedRecordType,
			String childLinkedRecordId) {
		return childLinkedRecordId.equals(linkedRecordId)
				&& childLinkedRecordType.equals(linkedRecordType);
	}

	protected String extractValueFromReferenceUsingNameInData(ClientDataGroup childReference,
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

	public String getNameInData() {
		if (null == matchingChildReference) {
			return "not found";
		}
		String childLinkedRecordType = extractValueFromReferenceUsingNameInData(
				matchingChildReference, "linkedRecordType");
		String childLinkedRecordId = extractValueFromReferenceUsingNameInData(
				matchingChildReference, "linkedRecordId");
		HttpHandler httpHandler = setUpHttpHandlerForReadingChildReference(childLinkedRecordType,
				childLinkedRecordId);
		String responseText = httpHandler.getResponseText();

		return responseText;

	}

}
