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

import se.uu.ub.cora.json.parser.JsonArray;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonString;
import se.uu.ub.cora.json.parser.JsonValue;

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
		JsonObject record = RecordHolder.getRecord();
		if (null != record) {
			JsonObject data = record.getValueAsJsonObject("data");
			JsonObject childReferences = tryToGetChildFromChildrenArrayByNameInData(data,
					"childReferences");
			List<JsonObject> childReferenceList = tryToGetChildrenByNameInData(childReferences,
					"childReference");
			for (JsonObject jsonObject : childReferenceList) {
				JsonObject ref = tryToGetChildFromChildrenArrayByNameInData(jsonObject, "ref");
				JsonObject linkedRecordType = tryToGetChildFromChildrenArrayByNameInData(ref,
						"linkedRecordType");
				JsonString linkedRecordTypeAsJsonString = linkedRecordType
						.getValueAsJsonString("value");
				String linkedRecordTypeAsStringValue = linkedRecordTypeAsJsonString
						.getStringValue();

				JsonObject linkedRecordId = tryToGetChildFromChildrenArrayByNameInData(ref,
						"linkedRecordId");
				JsonString linkedRecordIdAsJsonString = linkedRecordId
						.getValueAsJsonString("value");
				String linkedRecordIdAsStringValue = linkedRecordIdAsJsonString.getStringValue();

				if (this.linkedRecordType.equals(linkedRecordTypeAsStringValue)
						&& this.linkedRecordId.equals(linkedRecordIdAsStringValue)) {
					return true;
				}
			}
		}
		return false;
	}

	private JsonObject tryToGetChildFromChildrenArrayByNameInData(JsonObject jsonObject,
			String nameInData) {
		JsonArray children = jsonObject.getValueAsJsonArray("children");
		for (JsonValue child : children) {
			JsonObject jsonChildObject = (JsonObject) child;
			String name = jsonChildObject.getValueAsJsonString("name").getStringValue();
			if (nameInData.equals(name)) {
				return jsonChildObject;
			}
		}
		throw new ChildNotFoundException("child with name: " + nameInData + "not found");
	}

	private List<JsonObject> tryToGetChildrenByNameInData(JsonObject jsonObject,
			String nameInData) {
		JsonArray children = jsonObject.getValueAsJsonArray("children");
		List<JsonObject> foundChildren = new ArrayList<>();
		for (JsonValue child : children) {
			JsonObject jsonChildObject = (JsonObject) child;
			String name = jsonChildObject.getValueAsJsonString("name").getStringValue();
			if (nameInData.equals(name)) {
				foundChildren.add(jsonChildObject);
			}
		}
		return foundChildren;
	}

}
