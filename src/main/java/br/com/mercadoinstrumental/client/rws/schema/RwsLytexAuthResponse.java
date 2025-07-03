package br.com.mercadoinstrumental.client.rws.schema;

public class RwsLytexAuthResponse {

	private String accessToken;
	private String refreshToken;
	private String expireAt;
	private String refreshExpireAt;

	public RwsLytexAuthResponse() {
	}

	public RwsLytexAuthResponse(String accessToken, String refreshToken, String expireAt, String refreshExpireAt) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expireAt = expireAt;
		this.refreshExpireAt = refreshExpireAt;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(String expireAt) {
		this.expireAt = expireAt;
	}

	public String getRefreshExpireAt() {
		return refreshExpireAt;
	}

	public void setRefreshExpireAt(String refreshExpireAt) {
		this.refreshExpireAt = refreshExpireAt;
	}

}
