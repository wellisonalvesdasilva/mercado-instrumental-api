package br.com.mercadoinstrumental.client.rws.schema;

public class RwsLytexPaymentTypeBoletoReq {

	private boolean enable;

	public RwsLytexPaymentTypeBoletoReq(boolean enable) {
		this.enable = enable;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
