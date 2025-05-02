package br.com.bancoamazonia.sigaf.client.rws.data;

import java.util.Collection;
import java.util.Map;

public class RwsDocumentoResponse {

	public String base64;
	public Map<String, Collection<String>> headers;

	public RwsDocumentoResponse(String base64, Map<String, Collection<String>> headers) {
		super();
		this.base64 = base64;
		this.headers = headers;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public Map<String, Collection<String>> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, Collection<String>> headers) {
		this.headers = headers;
	}

}
