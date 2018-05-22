package se.uu.ub.cora.client;

public interface CoraRestClientFactory {

	CoraRestClient factorUsingUrlAndAuthToken(String baseUrl, String authToken);

}