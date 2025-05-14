package br.com.mercadoinstrumental.domain.model.anuncio;

import br.com.mercadoinstrumental.enums.ItemValorDescricao;

public enum StatusAnuncioEnum implements ItemValorDescricao {

	RASCUNHO("Rascunho"),
	PUBLICADO("Publicado");
	
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

}
