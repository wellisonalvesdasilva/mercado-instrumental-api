package br.com.mercadoinstrumental.client.rws.schema;

public class RwsLytexPaymentResponse {

	private String _hashId;
	private String _id;
	private String _recipientId;

	public RwsLytexPaymentResponse(String _hashId, String _id, String _recipientId) {
		this._hashId = _hashId;
		this._id = _id;
		this._recipientId = _recipientId;
	}

	public String get_hashId() {
		return _hashId;
	}

	public void set_hashId(String _hashId) {
		this._hashId = _hashId;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_recipientId() {
		return _recipientId;
	}

	public void set_recipientId(String _recipientId) {
		this._recipientId = _recipientId;
	}

}
