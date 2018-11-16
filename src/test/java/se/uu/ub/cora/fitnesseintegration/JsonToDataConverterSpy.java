package se.uu.ub.cora.fitnesseintegration;

import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.converter.jsontojava.JsonToDataConverter;

public class JsonToDataConverterSpy implements JsonToDataConverter {

	public boolean toInstanceWasCalled = false;

	@Override
	public ClientDataElement toInstance() {
		toInstanceWasCalled = true;
		return null;
	}

}
