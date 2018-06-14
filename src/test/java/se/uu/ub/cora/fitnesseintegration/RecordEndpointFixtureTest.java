package se.uu.ub.cora.fitnesseintegration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientDataRecord;

public class RecordEndpointFixtureTest {
	private RecordEndpointFixture fixture;
	private HttpHandlerFactorySpy httpHandlerFactorySpy;

	@BeforeMethod
	public void setUp() {
		SystemUrl.setUrl("http://localhost:8080/therest/");
		AuthTokenHolder.setAdminAuthToken("someAdminToken");
		DependencyProvider.setHttpHandlerFactoryClassName(
				"se.uu.ub.cora.fitnesseintegration.HttpHandlerFactorySpy");
		httpHandlerFactorySpy = (HttpHandlerFactorySpy) DependencyProvider.getFactory();
		fixture = new RecordEndpointFixture();
	}

	@Test
	public void testReadRecordDataForFactoryIsOk() {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.testReadRecord();
		assertEquals(httpHandlerFactorySpy.httpHandlerSpy.requestMetod, "GET");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId");
		assertEquals(httpHandlerFactorySpy.httpHandlerSpy.requestProperties.get("authToken"),
				"someToken");
	}

	@Test
	public void testReadRecordOk() {
		assertEquals(fixture.testReadRecord(), "Everything ok");
		assertEquals(fixture.getStatusType(), Response.Status.OK);
	}

	@Test
	public void testReadRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testReadRecord(), "bad things happend");
	}

	@Test
	public void testReadRecordShortenedUrlsAndOnlyFirstUpdatedShortenedUrl() {
		fixture.setType("url");
		fixture.setId("toshorten");
		assertEquals(fixture.testReadRecordShortenedUrlsAndOnlyFirstUpdated(),
				"{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\","
						+ "\"url\":\".../record/recordType/place\","
						+ "\"accept\":\"application/vnd.uub.record+json\"}}");
		assertEquals(fixture.getStatusType(), Response.Status.OK);
	}

	@Test
	public void testReadRecordShortenedUrlsAndOnlyFirstUpdatedOnlyFirst() {
		fixture.setType("updated");
		fixture.setId("morethanone");
		assertEquals(fixture.testReadRecordShortenedUrlsAndOnlyFirstUpdated(),
				"{\"children\":[{\"children\":[{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"recordType\"},"
						+ "{\"name\":\"linkedRecordId\",\"value\":\"place\"}],\"name\":\"type\"},{\"name\":\"id\","
						+ "\"value\":\"alvin-place:24\"},{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"system\"},"
						+ "{\"name\":\"linkedRecordId\",\"value\":\"alvin\"}],\"name\":\"dataDivider\"},{\"children\":["
						+ "{\"name\":\"linkedRecordType\",\"value\":\"user\"},{\"name\":\"linkedRecordId\","
						+ "\"value\":\"12345\"}],\"name\":\"createdBy\"},{\"name\":\"tsCreated\","
						+ "\"value\":\"2014-12-18 22:16:44.623\"},{\"repeatId\":\"0\",\"children\":[{\"children\":["
						+ "{\"name\":\"linkedRecordType\",\"value\":\"user\"},{\"name\":\"linkedRecordId\","
						+ "\"value\":\"12345\"}],\"name\":\"updatedBy\"},{\"name\":\"tsUpdated\","
						+ "\"value\":\"2014-12-18 22:16:44.623\"}],\"name\":\"updated\"}],\"name\":\"recordInfo\"}],"
						+ "\"name\":\"authority\",\"attributes\":{\"type\":\"place\"}}");
		assertEquals(fixture.getStatusType(), Response.Status.OK);
	}

	@Test
	public void testReadRecordShortenedUrlsAndOnlyFirstUpdated() {
		fixture.setType("place");
		fixture.setId("alvin-place:22");
		assertEquals(fixture.testReadRecordShortenedUrlsAndOnlyFirstUpdated(),
				"{\"record\":{\"data\":{\"children\":[{\"children\":[{\"children\":["
						+ "{\"name\":\"linkedRecordType\",\"value\":\"recordType\"},{\"name\":\"linkedRecordId\","
						+ "\"value\":\"place\"}],\"actionLinks\":{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\","
						+ "\"url\":\".../record/recordType/place\",\"accept\":\"application/vnd.uub.record+json\"}}"
						+ ",\"name\":\"type\"},{\"name\":\"id\",\"value\":\"alvin-place:22\"},{\"children\":["
						+ "{\"name\":\"linkedRecordType\",\"value\":\"system\"},{\"name\":\"linkedRecordId\","
						+ "\"value\":\"alvin\"}],\"actionLinks\":{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\","
						+ "\"url\":\".../record/system/alvin\",\"accept\":\"application/vnd.uub.record+json\"}},"
						+ "\"name\":\"dataDivider\"},{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"user\"},"
						+ "{\"name\":\"linkedRecordId\",\"value\":\"12345\"}],\"actionLinks\":{\"read\":"
						+ "{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\".../record/user/12345\","
						+ "\"accept\":\"application/vnd.uub.record+json\"}},\"name\":\"createdBy\"},"
						+ "{\"name\":\"tsCreated\",\"value\":\"2014-12-18 20:20:38.346\"},{\"repeatId\":\"0\","
						+ "\"children\":[{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"user\"},"
						+ "{\"name\":\"linkedRecordId\",\"value\":\"12345\"}],\"actionLinks\":{\"read\":"
						+ "{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\".../record/user/12345\","
						+ "\"accept\":\"application/vnd.uub.record+json\"}},\"name\":\"updatedBy\"},"
						+ "{\"name\":\"tsUpdated\",\"value\":\"2014-12-18 20:20:38.346\"}],\"name\":\"updated\"}],"
						+ "\"name\":\"recordInfo\"},{\"children\":[{\"children\":[{\"name\":\"value\","
						+ "\"value\":\"Linköping\"}],\"name\":\"namePart\",\"attributes\":{\"type\":\"defaultName\"}}],"
						+ "\"name\":\"name\",\"attributes\":{\"type\":\"authorized\"}},{\"children\":["
						+ "{\"name\":\"latitude\",\"value\":\"58.42\"},{\"name\":\"longitude\",\"value\":\"15.62\"}],"
						+ "\"name\":\"coordinates\"},{\"name\":\"country\",\"value\":\"SE\"}],\"name\":\"authority\"},"
						+ "\"actionLinks\":{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\","
						+ "\"url\":\".../record/place/alvin-place:22\",\"accept\":\"application/vnd.uub.record+json\"},"
						+ "\"update\":{\"requestMethod\":\"POST\",\"rel\":\"update\","
						+ "\"contentType\":\"application/vnd.uub.record+json\","
						+ "\"url\":\".../record/place/alvin-place:22\",\"accept\":\"application/vnd.uub.record+json\"},"
						+ "\"index\":{\"requestMethod\":\"POST\",\"rel\":\"index\",\"body\":{\"children\":["
						+ "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"recordType\"},"
						+ "{\"name\":\"linkedRecordId\",\"value\":\"place\"}],\"name\":\"recordType\"},"
						+ "{\"name\":\"recordId\",\"value\":\"alvin-place:22\"},{\"name\":\"type\",\"value\":\"index\"}],"
						+ "\"name\":\"workOrder\"},\"contentType\":\"application/vnd.uub.record+json\","
						+ "\"url\":\".../record/workOrder/\",\"accept\":\"application/vnd.uub.record+json\"},"
						+ "\"delete\":{\"requestMethod\":\"DELETE\",\"rel\":\"delete\","
						+ "\"url\":\".../record/place/alvin-place:22\"}}}}");
		assertEquals(fixture.getStatusType(), Response.Status.OK);
	}

	@Test
	public void testReadIncomingLinksDataForFactoryIsOk() {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.testReadIncomingLinks();
		assertEquals(httpHandlerFactorySpy.httpHandlerSpy.requestMetod, "GET");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId/incomingLinks");
		assertEquals(httpHandlerFactorySpy.httpHandlerSpy.requestProperties.get("authToken"),
				"someToken");
	}

	@Test
	public void testReadIncomingLinksOk() {
		assertEquals(fixture.testReadIncomingLinks(), "Everything ok");
	}

	@Test
	public void testReadIncomingLinksNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testReadIncomingLinks(), "bad things happend");
	}

	@Test
	public void testReadRecordListDataForFactoryIsOk() throws UnsupportedEncodingException {
		fixture.setType("someType");
		fixture.setAuthToken("someToken");

		fixture.testReadRecordList();
		assertEquals(httpHandlerFactorySpy.httpHandlerSpy.requestMetod, "GET");

		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType");
		assertEquals(httpHandlerFactorySpy.httpHandlerSpy.requestProperties.get("authToken"),
				"someToken");
	}

	@Test
	public void testReadRecordListNoFilterOk() throws UnsupportedEncodingException {
		assertEquals(fixture.testReadRecordList(), "Everything ok");
	}

	@Test
	public void testReadRecordListWithAddedFilterOk() throws UnsupportedEncodingException {
		fixture.setType("someType");
		fixture.setAuthToken("someToken");
		String json = "{\"name\":\"filter\",\"children\":[{\"name\":\"part\",\"children\":[{\"name\":\"key\",\"value\":\"idFromLogin\"},{\"name\":\"value\",\"value\":\"someId\"}],\"repeatId\":\"0\"}]}";

		fixture.setJson(json);
		String encodedJson = URLEncoder.encode(json, "UTF-8");
		assertEquals(fixture.testReadRecordList(), "Everything ok");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType?filter=" + encodedJson);
		assertEquals(httpHandlerFactorySpy.httpHandlerSpy.requestProperties.get("authToken"),
				"someToken");
	}

	@Test
	public void testReadRecordListNotOk() throws UnsupportedEncodingException {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testReadRecordList(), "bad things happend");
	}

	@Test
	public void testCreateRecordDataForFactoryIsOk() {
		httpHandlerFactorySpy.setResponseCode(201);
		fixture.setType("autogeneratedIdType");
		fixture.setAuthToken("someToken");
		fixture.setJson("{\"name\":\"value\"}");
		fixture.testCreateRecord();
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestMetod, "POST");
		assertEquals(httpHandlerSpy.outputString, "{\"name\":\"value\"}");
		assertEquals(httpHandlerSpy.requestProperties.get("Accept"),
				"application/vnd.uub.record+json");
		assertEquals(httpHandlerSpy.requestProperties.get("Content-Type"),
				"application/vnd.uub.record+json");
		assertEquals(httpHandlerSpy.requestProperties.get("authToken"), "someToken");
		assertEquals(httpHandlerSpy.requestProperties.size(), 3);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/autogeneratedIdType");
		assertEquals(fixture.getCreatedId(), "someRecordType:35824453170224822");
	}

	@Test
	public void testCreateAppTokenDataForFactoryIsOk() {
		httpHandlerFactorySpy.setResponseCode(201);
		fixture.setType("appToken");
		fixture.setAuthToken("someToken");
		fixture.setJson("{\"name\":\"value\"}");
		fixture.testCreateRecord();
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestMetod, "POST");
		assertEquals(httpHandlerSpy.outputString, "{\"name\":\"value\"}");
		assertEquals(httpHandlerSpy.requestProperties.get("Accept"),
				"application/vnd.uub.record+json");
		assertEquals(httpHandlerSpy.requestProperties.get("Content-Type"),
				"application/vnd.uub.record+json");
		assertEquals(httpHandlerSpy.requestProperties.get("authToken"), "someToken");
		assertEquals(httpHandlerSpy.requestProperties.size(), 3);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/appToken");
		assertEquals(fixture.getCreatedId(), "someRecordType:35824453170224822");
		assertEquals(fixture.getToken(), "ba064c86-bd7c-4283-a5f3-86ba1dade3f3");
	}

	@Test
	public void testCreateRecordOk() {
		httpHandlerFactorySpy.setResponseCode(201);
		assertEquals(fixture.testCreateRecord(), "Everything ok");
	}

	@Test
	public void testCreateRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testCreateRecord(), "bad things happend");
	}

	@Test
	public void testCreateRecordCreatedType() {
		httpHandlerFactorySpy.setResponseCode(201);
		fixture.setType("someRecordType");
		String createdType = fixture.testCreateRecordCreatedType();
		assertEquals(createdType, "someRecordType");
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestProperties.get("authToken"), "someAdminToken");
	}

	@Test
	public void testCreateRecordCreatedTypeNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testCreateRecordCreatedType(), "bad things happend");
	}

	@Test
	public void testCreateRecordCreatedTypeNotFoundInJson() {
		httpHandlerFactorySpy.setResponseCode(201);
		fixture.setType("someWrongRecordTypeWrongJson");
		String createdType = fixture.testCreateRecordCreatedType();
		assertEquals(createdType, "");
	}

	@Test
	public void testUpdateRecordDataForFactoryIsOk() {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.setJson("{\"name\":\"value\"}");
		fixture.testUpdateRecord();
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestMetod, "POST");
		assertEquals(httpHandlerSpy.outputString, "{\"name\":\"value\"}");
		assertEquals(httpHandlerSpy.requestProperties.get("Accept"),
				"application/vnd.uub.record+json");
		assertEquals(httpHandlerSpy.requestProperties.get("Content-Type"),
				"application/vnd.uub.record+json");
		assertEquals(httpHandlerSpy.requestProperties.get("authToken"), "someToken");
		assertEquals(httpHandlerSpy.requestProperties.size(), 3);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId");
	}

	@Test
	public void testUpdateRecordOk() {
		assertEquals(fixture.testUpdateRecord(), "Everything ok");
	}

	@Test
	public void testUpdateRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testUpdateRecord(), "bad things happend");
	}

	@Test
	public void testDeleteRecordDataForFactoryIsOk() {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.testDeleteRecord();
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestMetod, "DELETE");
		assertEquals(httpHandlerSpy.requestProperties.get("authToken"), "someToken");
		assertEquals(httpHandlerSpy.requestProperties.size(), 1);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId");
	}

	@Test
	public void testDeleteRecordOk() {
		assertEquals(fixture.testDeleteRecord(), "Everything ok");
	}

	@Test
	public void testDeleteRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testDeleteRecord(), "bad things happend");
	}

	@Test
	public void testUploadDataForFactoryIsOk() throws ClientProtocolException, IOException {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.setFileName("correctFileAnswer");
		fixture.testUpload();

		HttpMultiPartUploaderSpy httpHandlerSpy = httpHandlerFactorySpy.httpMultiPartUploaderSpy;
		assertEquals(httpHandlerSpy.headerFields.get("Accept"), "application/vnd.uub.record+json");
		assertEquals(httpHandlerSpy.headerFields.size(), 1);

		assertEquals(httpHandlerSpy.fieldName, "file");
		assertEquals(httpHandlerSpy.fileName, "correctFileAnswer");

		assertTrue(httpHandlerSpy.doneIsCalled);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId/master?authToken=someToken");
		assertEquals(fixture.getStreamId(), "soundBinary:23310456970967");
	}

	@Test
	public void testUploadDataForFactoryIsOkUsingDefaultAuthToken()
			throws ClientProtocolException, IOException {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setFileName("correctFileAnswer");
		fixture.testUpload();

		HttpMultiPartUploaderSpy httpHandlerSpy = httpHandlerFactorySpy.httpMultiPartUploaderSpy;
		assertEquals(httpHandlerSpy.headerFields.get("Accept"), "application/vnd.uub.record+json");
		assertEquals(httpHandlerSpy.headerFields.size(), 1);

		assertEquals(httpHandlerSpy.fieldName, "file");
		assertEquals(httpHandlerSpy.fileName, "correctFileAnswer");

		assertTrue(httpHandlerSpy.doneIsCalled);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId/master?authToken=someAdminToken");
		assertEquals(fixture.getStreamId(), "soundBinary:23310456970967");
	}

	@Test
	public void testUploadOk() throws ClientProtocolException, IOException {
		assertEquals(fixture.testUpload(), "Everything ok");
	}

	@Test
	public void testUploadNotOk() throws ClientProtocolException, IOException {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testUpload(), "bad things happend");
	}

	@Test
	public void testDownloadDataForFactoryIsOk() {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.setResourceName("someResourceName");
		fixture.testDownload();
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestMetod, "GET");
		assertEquals(httpHandlerSpy.requestProperties.get("authToken"), "someToken");
		assertEquals(httpHandlerSpy.requestProperties.size(), 1);

		assertEquals(fixture.getContentLength(), "9999");
		assertEquals(fixture.getContentDisposition(),
				"form-data; name=\"file\"; filename=\"adele.png\"\n");

		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId/someResourceName");
	}

	@Test
	public void testDownloadOk() {
		assertEquals(fixture.testDownload(), "Everything ok");
	}

	@Test
	public void testDownloadNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testDownload(), "bad things happend");
	}

	@Test
	public void testSearchRecordFactoryIsOk() throws UnsupportedEncodingException {
		fixture.setAuthToken("someToken");
		fixture.setSearchId("aSearchId");

		String json = "{\"name\":\"search\",\"children\":[{\"name\":\"include\",\"children\":["
				+ "{\"name\":\"includePart\",\"children\":[{\"name\":\"text\",\"value\":\"\"}]}]}]}";
		fixture.setJson(json);
		fixture.testSearchRecord();
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestMetod, "GET");
		assertEquals(httpHandlerSpy.requestProperties.get("authToken"), "someToken");
		assertEquals(httpHandlerSpy.requestProperties.size(), 1);

		String encodedJson = URLEncoder.encode(json, "UTF-8");

		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/searchResult/aSearchId/?" + "searchData="
						+ encodedJson);
	}

	@Test
	public void testSearchRecordOk() throws UnsupportedEncodingException {
		fixture.setAuthToken("someToken");
		fixture.setSearchId("aSearchId");

		String json = "{\"name\":\"search\",\"children\":[{\"name\":\"include\",\"children\":["
				+ "{\"name\":\"includePart\",\"children\":[{\"name\":\"text\",\"value\":\"\"}]}]}]}";
		fixture.setJson(json);
		assertEquals(fixture.testSearchRecord(), "Everything ok");
	}

	@Test
	public void testSearchRecordNotOk() throws UnsupportedEncodingException {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		fixture.setAuthToken("someToken");
		fixture.setSearchId("aSearchId");

		String json = "{\"name\":\"search\",\"children\":[{\"name\":\"include\",\"children\":["
				+ "{\"name\":\"includePart\",\"children\":[{\"name\":\"text\",\"value\":\"\"}]}]}]}";
		fixture.setJson(json);
		fixture.testSearchRecord();
		assertEquals(fixture.testReadRecordList(), "bad things happend");
	}

	@Test
	public void testSetJsonObject() {
		fixture.setType("metadataGroup");
		fixture.setId("someMetadataGroupId");
		fixture.setAuthToken("someToken");
		fixture.testReadRecordAndStoreJson();

		assertNotEquals(RecordHolder.getRecord(), null);
		assertTrue(RecordHolder.getRecord() instanceof ClientDataRecord);
	}
}
