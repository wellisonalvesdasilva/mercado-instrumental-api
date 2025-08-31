package br.com.mercadoinstrumental.controller.admin.indicacao.schema;

import java.time.LocalDate;

import br.com.mercadoinstrumental.controller.commom.schema.FilterPageable;
import br.com.mercadoinstrumental.controller.schema.geral.EnumResponse;
import br.com.mercadoinstrumental.model.usuario.TipoChavePixEnum;

public class IndicacaoFilter extends FilterPageable {

	private Long id;
	private String nome;
	private LocalDate dataCadastro;
	private String responsavelIndicacao;
	private Boolean visaoAdmin;
	private Long quantidadeIndicacoes;
	private Long quantidadeAnunciosPublicados;
	private Boolean hasComissaoPendente;
	private String chavePix;
	private String tiposChavePix;
	private String whats;

	public String getChavePix() {
		return chavePix;
	}

	public void setChavePix(String chavePix) {
		this.chavePix = chavePix;
	}

	public String getTiposChavePix() {
		return tiposChavePix;
	}

	public void setTiposChavePix(String tiposChavePix) {
		this.tiposChavePix = tiposChavePix;
	}

	public Long getQuantidadeIndicacoes() {
		return quantidadeIndicacoes;
	}

	public void setQuantidadeIndicacoes(Long quantidadeIndicacoes) {
		this.quantidadeIndicacoes = quantidadeIndicacoes;
	}

	public Long getQuantidadeAnunciosPublicados() {
		return quantidadeAnunciosPublicados;
	}

	public void setQuantidadeAnunciosPublicados(Long quantidadeAnunciosPublicados) {
		this.quantidadeAnunciosPublicados = quantidadeAnunciosPublicados;
	}

	public Boolean getHasComissaoPendente() {
		return hasComissaoPendente;
	}

	public void setHasComissaoPendente(Boolean hasComissaoPendente) {
		this.hasComissaoPendente = hasComissaoPendente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getVisaoAdmin() {
		return visaoAdmin;
	}

	public void setVisaoAdmin(Boolean visaoAdmin) {
		this.visaoAdmin = visaoAdmin;
	}

	public String getResponsavelIndicacao() {
		return responsavelIndicacao;
	}

	public void setResponsavelIndicacao(String responsavelIndicacao) {
		this.responsavelIndicacao = responsavelIndicacao;
	}

	public String getWhats() {
		return whats;
	}

	public void setWhats(String whats) {
		this.whats = whats;
	}

}