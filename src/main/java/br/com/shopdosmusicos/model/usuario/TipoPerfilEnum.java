package br.com.shopdosmusicos.model.usuario;

import br.com.shopdosmusicos.enums.ItemValorDescricao;

public enum TipoPerfilEnum implements ItemValorDescricao {

	ADMINISTRADOR(1, "ROLE_ADMINISTRADOR"), 
	ANUNCIANTE(2, "ROLE_ANUNCIANTE");

	private int cod;
	private String descricao;

	private TipoPerfilEnum(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoPerfilEnum toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}

		for (TipoPerfilEnum x : TipoPerfilEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + cod);
	}

	@Override
	public Object getValue() {
		return cod;
	}

	@Override
	public String getLabel() {
		return name();
	}

}