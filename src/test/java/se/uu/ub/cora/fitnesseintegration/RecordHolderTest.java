package se.uu.ub.cora.fitnesseintegration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataRecord;

public class RecordHolderTest {
	@Test
	public void testInit() {
		RecordHolder recordHolder = new RecordHolder();
		assertNotNull(recordHolder);
	}

	@Test
	public void testName() throws Exception {
		ClientDataGroup clientDataGroup = ClientDataGroup.withNameInData("someName");
		ClientDataRecord clientDataRecord = ClientDataRecord.withClientDataGroup(clientDataGroup);
		RecordHolder.setRecord(clientDataRecord);
		assertEquals(RecordHolder.getRecord(), clientDataRecord);
	}
}
