package br.com.mercadoinstrumental.client.rws.schema;

public class RwsLytexAuthReq {

	private String clientId;
	private String clientSecret;

	public RwsLytexAuthReq(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

}
