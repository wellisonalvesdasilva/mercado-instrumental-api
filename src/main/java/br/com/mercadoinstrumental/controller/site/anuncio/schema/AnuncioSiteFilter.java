package br.com.mercadoinstrumental.controller.site.anuncio.schema;

import java.math.BigDecimal;

import br.com.mercadoinstrumental.controller.commom.schema.FilterPageable;
import br.com.mercadoinstrumental.domain.model.anuncio.MarcaInstrumentoMusicalEnum;
import br.com.mercadoinstrumental.domain.model.anuncio.TipoInstrumentoMusicalEnum;

public class AnuncioSiteFilter extends FilterPageable {

	private String titulo;
	private String descricao;
	private TipoInstrumentoMusicalEnum tipo;
	private MarcaInstrumentoMusicalEnum marca;
	private Boolean condicao;
	private String estado;
	private String municipio;
	private BigDecimal precoMin;
	private BigDecimal precoMax;

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

	public Boolean getCondicao() {
		return condicao;
	}

	public void setCondicao(Boolean condicao) {
		this.condicao = condicao;
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

	public BigDecimal getPrecoMin() {
		return precoMin;
	}

	public void setPrecoMin(BigDecimal precoMin) {
		this.precoMin = precoMin;
	}

	public BigDecimal getPrecoMax() {
		return precoMax;
	}

	public void setPrecoMax(BigDecimal precoMax) {
		this.precoMax = precoMax;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}