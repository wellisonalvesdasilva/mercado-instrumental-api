package br.com.mercadoinstrumental.domain.model.anuncio;

import br.com.mercadoinstrumental.enums.ItemValorDescricao;

public enum TipoInstrumentoMusicalEnum implements ItemValorDescricao {

    MADEIRAS("Avaliação"), 
    METAIS("Ordem de Serviço"),
    TECLADOS("Ordem de Serviço"),
    CORDAS("Ordem de Serviço");

    private String label;

	private TipoInstrumentoMusicalEnum(String label) {
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
