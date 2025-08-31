package br.com.mercadoinstrumental.model.usuario;

import br.com.mercadoinstrumental.domain.model.anuncio.StatusAnuncioEnum;
import br.com.mercadoinstrumental.enums.ItemValorDescricao;

public enum TipoChavePixEnum implements ItemValorDescricao {

	CPF_CNPJ("CPF/CNPJ"), 
	EMAIL("E-mail"), 
	NU_TELEFONE("Número de Telefone"), 
	CHAVE_ALEATORIA("Chave Aleatória"),
	QR_CODE("QR Code");

	private String label;

	private TipoChavePixEnum(String label) {
		this.label = label;
	}

	@Override
	public Object getValue() {
		return name();
	}

	@Override
	public String getLabel() {
		return label;
	}


	 public static TipoChavePixEnum fromLabel(String label) {
	        for (TipoChavePixEnum status : values()) {
	            if (status.label.equalsIgnoreCase(label)) {
	                return status;
	            }
	        }
	        throw new IllegalArgumentException("Status inválido: " + label);
	    }

}