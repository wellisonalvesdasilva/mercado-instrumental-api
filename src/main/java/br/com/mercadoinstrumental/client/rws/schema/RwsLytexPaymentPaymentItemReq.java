package br.com.mercadoinstrumental.client.rws.schema;

public class RwsLytexPaymentPaymentItemReq {

	private String name;
	private Integer value;
	private String quantity;

	public RwsLytexPaymentPaymentItemReq(String name, Integer value, String quantity) {
		this.name = name;
		this.value = value;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
