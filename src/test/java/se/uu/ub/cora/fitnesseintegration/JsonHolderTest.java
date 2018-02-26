package se.uu.ub.cora.fitnesseintegration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParser;
import se.uu.ub.cora.json.parser.org.OrgJsonParser;

public class JsonHolderTest {
	@Test
	public void testInit() {
		JsonHolder jsonHolder = new JsonHolder();
		assertNotNull(jsonHolder);
	}

	@Test
	public void testName() throws Exception {
		JsonParser jsonParser = new OrgJsonParser();
		JsonObject jsonValue = (JsonObject) jsonParser.parseString("{\"trams\":\"trams\"}");
		JsonHolder.setJson(jsonValue);
		assertEquals(JsonHolder.getJson(), jsonValue);
	}
}
