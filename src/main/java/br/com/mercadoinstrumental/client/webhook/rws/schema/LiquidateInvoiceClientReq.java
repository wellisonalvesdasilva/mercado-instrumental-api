package br.com.mercadoinstrumental.client.webhook.rws.schema;

public class LiquidateInvoiceClientReq {

    private String name;
    private String cpfCnpj;
    private String cellphone;
    private String email;
    private ClientAddressReq address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClientAddressReq getAddress() {
        return address;
    }

    public void setAddress(ClientAddressReq address) {
        this.address = address;
    }
}