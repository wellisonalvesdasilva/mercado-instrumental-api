package br.com.shopdosmusicos.domain.model.anuncio;

import br.com.shopdosmusicos.enums.ItemValorDescricao;

public enum MarcaInstrumentoMusical implements ItemValorDescricao {

	YAMAHA("Yamaha"),
	SELMER("Semler"),
	BACH("Bach"),
	GETZEN("Getzen"),
	JUPITER("Jupiter"),
	KING("King"),
	P_MAURIAT("P. Mauriat");
	
    private String label;

	private MarcaInstrumentoMusical(String label) {
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
