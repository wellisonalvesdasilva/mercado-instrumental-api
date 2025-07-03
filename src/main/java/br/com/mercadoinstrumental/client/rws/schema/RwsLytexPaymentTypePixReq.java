package br.com.mercadoinstrumental.client.rws.schema;

import java.math.BigDecimal;

public class RwsLytexPaymentTypePixReq {

	private boolean enable;

	public RwsLytexPaymentTypePixReq(boolean enable) {
		this.enable = enable;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
