package se.uu.ub.cora.fitnesseintegration;

import se.uu.ub.cora.clientdata.ClientDataRecord;

public class RecordHolder {

	public RecordHolder() {
		// needed by fitnesse
		super();
	}

	private static ClientDataRecord clientDataRecord;

	public static void setRecord(ClientDataRecord clientDataRecord) {
		RecordHolder.clientDataRecord = clientDataRecord;
	}

	public static ClientDataRecord getRecord() {
		return clientDataRecord;
	}

}
