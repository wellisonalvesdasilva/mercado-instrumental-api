package br.com.mercadoinstrumental.domain.model.anuncio;

import br.com.mercadoinstrumental.enums.ItemValorDescricao;

public enum TipoInstrumentoMusicalEnum implements ItemValorDescricao {

	MADEIRAS("Madeiras"), METAIS("Metais"), TECLADOS("Teclados"), CORDAS("Cordas");

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

	public static TipoInstrumentoMusicalEnum fromLabel(String label) {
		for (TipoInstrumentoMusicalEnum tipo : values()) {
			if (tipo.label.equalsIgnoreCase(label)) {
				return tipo;
			}
		}
		throw new IllegalArgumentException("Status inválido: " + label);
	}

}
