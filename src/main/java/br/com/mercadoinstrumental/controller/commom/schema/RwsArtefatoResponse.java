package br.com.mercadoinstrumental.controller.commom.schema;

public class RwsArtefatoResponse {

	public String name;
	public String base64;
	public String headers;

	public RwsArtefatoResponse(String name, String base64, String headers) {
		this.name = name;
		this.base64 = base64;
		this.headers = headers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

}
