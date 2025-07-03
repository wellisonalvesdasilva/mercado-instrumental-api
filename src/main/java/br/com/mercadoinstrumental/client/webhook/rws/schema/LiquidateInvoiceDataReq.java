package br.com.mercadoinstrumental.client.webhook.rws.schema;

import java.time.ZonedDateTime;

public class LiquidateInvoiceDataReq {

	private String invoiceId;
	private String status;
	private ZonedDateTime payedAt;
	private Integer payedValue;
	private Integer invoiceValue;
	private Integer discount;
	private Integer mulct;
	private Integer interest;
	private String paymentMethod;
	private LiquidateInvoiceClientReq client;
	private String referenceId;
	private String _installmentId;
	private String _subscriptionId;
	private String _paymentLinkId;
	private CustomFieldsReq customFields;
	private String creditAt;
	private String dueDate;
	private Integer rates;

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ZonedDateTime getPayedAt() {
		return payedAt;
	}

	public void setPayedAt(ZonedDateTime payedAt) {
		this.payedAt = payedAt;
	}

	public Integer getPayedValue() {
		return payedValue;
	}

	public void setPayedValue(Integer payedValue) {
		this.payedValue = payedValue;
	}

	public Integer getInvoiceValue() {
		return invoiceValue;
	}

	public void setInvoiceValue(Integer invoiceValue) {
		this.invoiceValue = invoiceValue;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getMulct() {
		return mulct;
	}

	public void setMulct(Integer mulct) {
		this.mulct = mulct;
	}

	public Integer getInterest() {
		return interest;
	}

	public void setInterest(Integer interest) {
		this.interest = interest;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public LiquidateInvoiceClientReq getClient() {
		return client;
	}

	public void setClient(LiquidateInvoiceClientReq client) {
		this.client = client;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String get_installmentId() {
		return _installmentId;
	}

	public void set_installmentId(String _installmentId) {
		this._installmentId = _installmentId;
	}

	public String get_subscriptionId() {
		return _subscriptionId;
	}

	public void set_subscriptionId(String _subscriptionId) {
		this._subscriptionId = _subscriptionId;
	}

	public String get_paymentLinkId() {
		return _paymentLinkId;
	}

	public void set_paymentLinkId(String _paymentLinkId) {
		this._paymentLinkId = _paymentLinkId;
	}

	public CustomFieldsReq getCustomFields() {
		return customFields;
	}

	public void setCustomFields(CustomFieldsReq customFields) {
		this.customFields = customFields;
	}

	public String getCreditAt() {
		return creditAt;
	}

	public void setCreditAt(String creditAt) {
		this.creditAt = creditAt;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getRates() {
		return rates;
	}

	public void setRates(Integer rates) {
		this.rates = rates;
	}
}
