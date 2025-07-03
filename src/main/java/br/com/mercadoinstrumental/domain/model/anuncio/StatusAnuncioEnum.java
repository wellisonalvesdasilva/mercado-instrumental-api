package br.com.mercadoinstrumental.domain.model.anuncio;

import br.com.mercadoinstrumental.enums.ItemValorDescricao;

public enum StatusAnuncioEnum implements ItemValorDescricao {

	RASCUNHO("Rascunho"),
	AGUARDANDO_PUBLICACAO("Aguardando Publicação"),
	PUBLICADO("Publicado"),
	AGUARDANDO_CONFIRMACAO_PAGAMENTO("Aguardando Confirmação de Pagamento"),
	EXPIRADO("Expirado");
	
    private String label;

	private StatusAnuncioEnum(String label) {
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

	 public static StatusAnuncioEnum fromLabel(String label) {
	        for (StatusAnuncioEnum status : values()) {
	            if (status.label.equalsIgnoreCase(label)) {
	                return status;
	            }
	        }
	        throw new IllegalArgumentException("Status inválido: " + label);
	    }

}
