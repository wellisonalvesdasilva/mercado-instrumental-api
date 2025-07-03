package br.com.mercadoinstrumental.client.rws.schema;

import java.math.BigDecimal;

public class RwsLytexPaymentTypeCreditCardReq {

	private boolean enable;
	private int parcels;

	public RwsLytexPaymentTypeCreditCardReq(boolean enable, int parcels) {
		this.enable = enable;
		this.parcels = parcels;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getParcels() {
		return parcels;
	}

	public void setParcels(int parcels) {
		this.parcels = parcels;
	}

}
