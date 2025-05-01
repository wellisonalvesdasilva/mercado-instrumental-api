package br.com.shopdosmusicos.domain.model.anuncio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.shopdosmusicos.model.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ANUNCIO")
public class Anuncio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ANUNCIO")
	private Long id;

	@NotNull
	@Size(max = 100)
	@Column(name = "NO_ANUNCIO")
	private String nome;

	@Size(max = 4000)
	@Column(name = "DS_ANUNCIO")
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(name = "EN_TIPO")
	private TipoInstrumentoMusicalEnum tipo;

	@Enumerated(EnumType.STRING)
	@Column(name = "EN_MARCA")
	private MarcaInstrumentoMusical marca;

	@NotNull
	@Column(name = "ID_MUNICIPIO_IBGE")
	private Long idMunicipioIbge;

	@Column(name = "VL_ANUNCIO")
	private BigDecimal valor;

	@NotNull
	@Column(name = "IN_NOVO")
	private Boolean novo;

	@NotNull
	@Column(name = "IN_ATIVO")
	private Boolean ativo;

	@NotNull
	@Column(name = "DH_PUBLICACAO")
	private LocalDateTime dataHoraPublicacao;

	@NotNull
	@Column(name = "QTDE_ACESSO")
	private Long quantidadeAcesso;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;

	public Anuncio() {
	}

	public Anuncio(@NotNull @Size(max = 100) String nome, @Size(max = 4000) String descricao,
			TipoInstrumentoMusicalEnum tipo, MarcaInstrumentoMusical marca, @NotNull Long idMunicipioIbge,
			BigDecimal valor, @NotNull Boolean novo, @NotNull Usuario usuario) {
		this.nome = nome;
		this.descricao = descricao;
		this.tipo = tipo;
		this.marca = marca;
		this.idMunicipioIbge = idMunicipioIbge;
		this.valor = valor;
		this.novo = novo;
		this.dataHoraPublicacao = LocalDateTime.now();
		this.quantidadeAcesso = 0L;
		this.usuario = usuario;
		this.ativo = false;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoInstrumentoMusicalEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoInstrumentoMusicalEnum tipo) {
		this.tipo = tipo;
	}

	public MarcaInstrumentoMusical getMarca() {
		return marca;
	}

	public void setMarca(MarcaInstrumentoMusical marca) {
		this.marca = marca;
	}

	public Long getIdMunicipioIbge() {
		return idMunicipioIbge;
	}

	public void setIdMunicipioIbge(Long idMunicipioIbge) {
		this.idMunicipioIbge = idMunicipioIbge;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public LocalDateTime getDataHoraPublicacao() {
		return dataHoraPublicacao;
	}

	public void setDataHoraPublicacao(LocalDateTime dataHoraPublicacao) {
		this.dataHoraPublicacao = dataHoraPublicacao;
	}

	public Long getQuantidadeAcesso() {
		return quantidadeAcesso;
	}

	public void setQuantidadeAcesso(Long quantidadeAcesso) {
		this.quantidadeAcesso = quantidadeAcesso;
	}

}
