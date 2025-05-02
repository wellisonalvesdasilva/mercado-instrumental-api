package br.com.shopdosmusicos.controller.admin.anuncio.schema;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.shopdosmusicos.controller.commom.schema.FilterPageable;
import br.com.shopdosmusicos.domain.model.anuncio.MarcaInstrumentoMusicalEnum;
import br.com.shopdosmusicos.domain.model.anuncio.TipoInstrumentoMusicalEnum;

public class AnuncioFilter extends FilterPageable {

	private String titulo;
	private TipoInstrumentoMusicalEnum tipo;
	private MarcaInstrumentoMusicalEnum marca;
	private String estado;
	private String municipio;
	private BigDecimal valor;
	private Boolean novo;
	private Boolean ativo;
	private LocalDate dataPublicacao;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public TipoInstrumentoMusicalEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoInstrumentoMusicalEnum tipo) {
		this.tipo = tipo;
	}

	public MarcaInstrumentoMusicalEnum getMarca() {
		return marca;
	}

	public void setMarca(MarcaInstrumentoMusicalEnum marca) {
		this.marca = marca;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Boolean getNovo() {
		return novo;
	}

	public void setNovo(Boolean novo) {
		this.novo = novo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(LocalDate dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

}