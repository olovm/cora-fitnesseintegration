package se.uu.ub.cora.fitnesseintegration;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import se.uu.ub.cora.httphandler.HttpHandler;
import se.uu.ub.cora.httphandler.HttpHandlerFactory;

public class AppTokenEndpointFixture {
	private static final int DISTANCE_TO_START_OF_TOKEN = 21;
	private String appToken;
	private HttpHandlerFactory factory;
	private String baseUrl = SystemUrl.getAppTokenVerifierUrl() + "rest/apptoken/";
	private String userId;
	private Status statusType;
	private String authToken;
	private String authTokenToLogOut;

	public AppTokenEndpointFixture() {
		factory = DependencyProvider.getFactory();
	}

	public void setAppToken(String token) {
		this.appToken = token;
	}

	public String getAuthTokenForAppToken() {
		String url = baseUrl + userId;

		HttpHandler httpHandler = factory.factor(url);
		httpHandler.setRequestMethod("POST");
		if (appToken == null || "".equals(appToken)) {
			if ("131313".equals(userId)) {
				appToken = "44c17361-ead7-43b5-a938-038765873037";
			} else if ("121212".equals(userId)) {
				appToken = "a5b9871f-1610-44e1-b838-c37ace6757d6";
			}
		}
		httpHandler.setOutput(appToken);

		statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());
		if (statusType.equals(Response.Status.CREATED)) {
			String responseText = httpHandler.getResponseText();
			authToken = extractCreatedTokenFromResponseText(responseText);

			return responseText;
		}
		return httpHandler.getErrorText();
	}

	private String extractCreatedTokenFromResponseText(String responseText) {
		int idIndex = responseText.lastIndexOf("\"name\":\"id\"") + DISTANCE_TO_START_OF_TOKEN;
		return responseText.substring(idIndex, responseText.indexOf('"', idIndex));
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public StatusType getStatusType() {
		return statusType;
	}

	public void setAuthTokenToLogOut(String authTokenToLogOut) {
		this.authTokenToLogOut = authTokenToLogOut;
	}

	public void removeAuthTokenForUser() {
		String url = baseUrl + userId;

		HttpHandler httpHandler = factory.factor(url);
		httpHandler.setRequestMethod("DELETE");
		httpHandler.setOutput(authTokenToLogOut);

		statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());
	}
}
