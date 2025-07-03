package br.com.mercadoinstrumental.client.rws.schema;

public class RwsLytexPaymentRedirectReq {

	private String redirectUrl;
	private boolean autoRedirect;

	public RwsLytexPaymentRedirectReq(String redirectUrl, boolean autoRedirect) {
		this.redirectUrl = redirectUrl;
		this.autoRedirect = autoRedirect;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public boolean isAutoRedirect() {
		return autoRedirect;
	}

	public void setAutoRedirect(boolean autoRedirect) {
		this.autoRedirect = autoRedirect;
	}

}
