package br.com.mercadoinstrumental.client.rws.schema;

public class RwsLytexPaymentItemReq {

	private RwsLytexPaymentTypePixReq pix;
	private RwsLytexPaymentTypeBoletoReq boleto;
	private RwsLytexPaymentTypeCreditCardReq creditCard;

	public RwsLytexPaymentItemReq(
			RwsLytexPaymentTypePixReq pix, 
			RwsLytexPaymentTypeBoletoReq boleto,
			RwsLytexPaymentTypeCreditCardReq creditCard) {
		this.pix = pix;
		this.boleto = boleto;
		this.creditCard = creditCard;
	}

	public RwsLytexPaymentTypePixReq getPix() {
		return pix;
	}

	public void setPix(RwsLytexPaymentTypePixReq pix) {
		this.pix = pix;
	}

	public RwsLytexPaymentTypeBoletoReq getBoleto() {
		return boleto;
	}

	public void setBoleto(RwsLytexPaymentTypeBoletoReq boleto) {
		this.boleto = boleto;
	}

	public RwsLytexPaymentTypeCreditCardReq getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(RwsLytexPaymentTypeCreditCardReq creditCard) {
		this.creditCard = creditCard;
	}

}
