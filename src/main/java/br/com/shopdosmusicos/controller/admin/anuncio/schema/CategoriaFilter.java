package br.com.shopdosmusicos.controller.admin.anuncio.schema;

import br.com.shopdosmusicos.controller.commom.schema.FilterPageable;

public class CategoriaFilter extends FilterPageable {

	public String nome;
	public String cor;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

}