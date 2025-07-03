package br.com.mercadoinstrumental.client.rws.schema;

import java.math.BigDecimal;
import java.util.List;

public class RwsLytexPaymentReq {

	private String description;
	private Integer totalValue;
	private String referenceId;
	private List<RwsLytexPaymentPaymentItemReq> items;
	private RwsLytexPaymentItemReq paymentMethods;
	private RwsLytexPaymentRedirectReq redirect;

	public RwsLytexPaymentReq(String description, Integer totalValue, String referenceId,
			List<RwsLytexPaymentPaymentItemReq> items, RwsLytexPaymentItemReq paymentMethods,
			RwsLytexPaymentRedirectReq redirect) {
		this.description = description;
		this.totalValue = totalValue;
		this.referenceId = referenceId;
		this.items = items;
		this.paymentMethods = paymentMethods;
		this.redirect = redirect;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Integer totalValue) {
		this.totalValue = totalValue;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public List<RwsLytexPaymentPaymentItemReq> getItems() {
		return items;
	}

	public void setItems(List<RwsLytexPaymentPaymentItemReq> items) {
		this.items = items;
	}

	public RwsLytexPaymentItemReq getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(RwsLytexPaymentItemReq paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	public RwsLytexPaymentRedirectReq getRedirect() {
		return redirect;
	}

	public void setRedirect(RwsLytexPaymentRedirectReq redirect) {
		this.redirect = redirect;
	}

}
