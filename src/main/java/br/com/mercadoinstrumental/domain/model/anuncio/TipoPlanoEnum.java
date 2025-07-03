package br.com.mercadoinstrumental.domain.model.anuncio;

import br.com.mercadoinstrumental.enums.ItemValorDescricao;

public enum TipoPlanoEnum implements ItemValorDescricao {

    GRATIS("Grátis", 0),
    BASICO("Básico", 250),
    PREMIUM("Premium", 4000),
    AVANCADO("Avançado", 6000);

    private String label;
    private int price;

    private TipoPlanoEnum(String label, int price) {
        this.label = label;
        this.price = price;
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

    public static TipoPlanoEnum fromLabel(String label) {
        for (TipoPlanoEnum status : values()) {
            if (status.label.equalsIgnoreCase(label)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + label);
    }
}
