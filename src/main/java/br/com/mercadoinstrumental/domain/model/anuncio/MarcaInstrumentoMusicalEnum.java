package br.com.mercadoinstrumental.domain.model.anuncio;

import br.com.mercadoinstrumental.enums.ItemValorDescricao;

public enum MarcaInstrumentoMusicalEnum implements ItemValorDescricao {

	WERIL("Weril"), 
	YAMAHA("Yamaha"), 
	SELMER("Semler"), 
	BACH("Bach"), 
	GETZEN("Getzen"), 
	JUPITER("Jupiter"),
	KING("King"), 
	QUASAR("Quasar"), 
	EAGLE("Eagle"), 
	MICHAEL("Michael"), 
	P_MAURIAT("P. Mauriat"), 
	OUTRA("Outra");

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

	public static MarcaInstrumentoMusicalEnum fromLabel(String label) {
		for (MarcaInstrumentoMusicalEnum tipo : values()) {
			if (tipo.label.equalsIgnoreCase(label)) {
				return tipo;
			}
		}
		throw new IllegalArgumentException("Status inválido: " + label);
	}

}
