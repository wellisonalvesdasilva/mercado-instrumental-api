package br.com.mercadoinstrumental.domain.model.anuncio;

import br.com.mercadoinstrumental.enums.ItemValorDescricao;

public enum TipoPlanoEnum implements ItemValorDescricao {

	GRATIS("Grátis", 0, 15), 
	BASICO("Básico", 250, 20), 
	PREMIUM("Premium", 4000, 30), 
	AVANCADO("Avançado", 6000, 60);

	private String label;
	private int price;
	private int days;

	private TipoPlanoEnum(String label, int price, int days) {
		this.label = label;
		this.price = price;
		this.days = days;
	}

	@Override
	public Object getValue() {
		return name();
	}

	@Override
	public String getLabel() {
		return label;
	}

	public int getPrice() {
		return price;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public static TipoPlanoEnum fromLabel(String label) {
		for (TipoPlanoEnum status : values()) {
			if (status.label.equalsIgnoreCase(label)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Status inválido: " + label);
	}
}
