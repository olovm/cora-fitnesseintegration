package se.uu.ub.cora.fitnesseintegration;

import se.uu.ub.cora.json.parser.JsonObject;

public class JsonHolder {

	public JsonHolder() {
		// needed by fitnesse
		super();
	}

	private static JsonObject json;

	public static void setJson(JsonObject jsonObject) {
		JsonHolder.json = jsonObject;
	}

	public static JsonObject getJson() {
		return json;
	}

}
