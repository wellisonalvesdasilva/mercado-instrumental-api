package br.com.mercadoinstrumental.domain.model.anuncio;

import br.com.mercadoinstrumental.enums.ItemValorDescricao;

public enum MarcaInstrumentoMusicalEnum implements ItemValorDescricao {

	YAMAHA("Yamaha"),
	SELMER("Semler"),
	BACH("Bach"),
	GETZEN("Getzen"),
	JUPITER("Jupiter"),
	KING("King"),
	P_MAURIAT("P. Mauriat");
	
    private String label;

	private MarcaInstrumentoMusicalEnum(String label) {
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
