package br.com.mercadoinstrumental.client.webhook.rws.schema;

public class LiquidateInvoiceWebhookReq {

    private String webhookType;
    private String signature;
    private LiquidateInvoiceDataReq data;

    public String getWebhookType() {
        return webhookType;
    }

    public void setWebhookType(String webhookType) {
        this.webhookType = webhookType;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public LiquidateInvoiceDataReq getData() {
        return data;
    }

    public void setData(LiquidateInvoiceDataReq data) {
        this.data = data;
    }
}

