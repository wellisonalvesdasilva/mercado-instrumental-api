package br.com.mercadoinstrumental.domain.model.anuncio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.mercadoinstrumental.model.usuario.Usuario;
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
	private String titulo;

	@Size(max = 4000)
	@Column(name = "DS_ANUNCIO")
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(name = "EN_TIPO")
	private TipoInstrumentoMusicalEnum tipo;

	@Enumerated(EnumType.STRING)
	@Column(name = "EN_MARCA")
	private MarcaInstrumentoMusicalEnum marca;

	@NotNull
	@Column(name = "NO_ESTADO")
	private String estado;

	@NotNull
	@Column(name = "NO_MUNICIPIO")
	private String municipio;

	@Column(name = "VL_ANUNCIO")
	private BigDecimal valor;

	@NotNull
	@Column(name = "IN_NOVO")
	private Boolean novo;

	@Column(name = "DH_PUBLICACAO")
	private LocalDateTime dataHoraPublicacao;

	@Column(name = "DH_EXPIRACAO")
	private LocalDateTime dataHoraExpiracao;

	@NotNull
	@Column(name = "QTDE_ACESSO")
	private Long quantidadeAcesso;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;

	@Enumerated(EnumType.STRING)
	@Column(name = "EN_STATUS")
	private StatusAnuncioEnum status;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "EN_TIPO_PLANO")
	private TipoPlanoEnum tipoPlano;

	@Column(name = "ID_LYTEX")
	private String idPagamentoLytex;

	@Column(name = "HASH_ID_LYTEX")
	private String hashIdPagamentoLytex;

	@NotNull
	@Column(name = "VL_PAGO_PLANO")
	private BigDecimal valorPagoPlano;

	@Column(name = "DT_PAGAMENTO_COMISSAO")
	private LocalDate dataPagamentoComissao;

	@Column(name = "NU_TRANSACAO_PAGAMENTO")
	private String numeroTransacaoPagamento;

	public Anuncio() {
	}

	public Anuncio(@NotNull @Size(max = 100) String titulo, @Size(max = 4000) String descricao,
			TipoInstrumentoMusicalEnum tipo, MarcaInstrumentoMusicalEnum marca, @NotNull String estado,
			@NotNull String municipio, BigDecimal valor, @NotNull Boolean novo, @NotNull Boolean ativo,
			@NotNull Usuario usuario, @NotNull TipoPlanoEnum tipoPlano) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.tipo = tipo;
		this.marca = marca;
		this.estado = estado;
		this.municipio = municipio;
		this.valor = valor;
		this.novo = novo;
		this.usuario = usuario;
		this.quantidadeAcesso = 0L;
		this.status = StatusAnuncioEnum.RASCUNHO;
		this.tipoPlano = tipoPlano;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void incrementarQtdeAcesso() {
		this.quantidadeAcesso = this.quantidadeAcesso + 1;
	}

	public StatusAnuncioEnum getStatus() {
		return status;
	}

	public void setStatus(StatusAnuncioEnum status) {
		this.status = status;
	}

	public TipoPlanoEnum getTipoPlano() {
		return tipoPlano;
	}

	public void setTipoPlano(TipoPlanoEnum tipoPlano) {
		this.tipoPlano = tipoPlano;
	}

	public LocalDateTime getDataHoraExpiracao() {
		return dataHoraExpiracao;
	}

	public void setDataHoraExpiracao(LocalDateTime dataHoraExpiracao) {
		this.dataHoraExpiracao = dataHoraExpiracao;
	}

	public String getIdPagamentoLytex() {
		return idPagamentoLytex;
	}

	public void setIdPagamentoLytex(String idPagamentoLytex) {
		this.idPagamentoLytex = idPagamentoLytex;
	}

	public String getHashIdPagamentoLytex() {
		return hashIdPagamentoLytex;
	}

	public void setHashIdPagamentoLytex(String hashIdPagamentoLytex) {
		this.hashIdPagamentoLytex = hashIdPagamentoLytex;
	}

	public BigDecimal getValorPagoPlano() {
		return valorPagoPlano;
	}

	public void setValorPagoPlano(BigDecimal valorPagoPlano) {
		this.valorPagoPlano = valorPagoPlano;
	}

	public LocalDate getDataPagamentoComissao() {
		return dataPagamentoComissao;
	}

	public void setDataPagamentoComissao(LocalDate dataPagamentoComissao) {
		this.dataPagamentoComissao = dataPagamentoComissao;
	}

	public String getNumeroTransacaoPagamento() {
		return numeroTransacaoPagamento;
	}

	public void setNumeroTransacaoPagamento(String numeroTransacaoPagamento) {
		this.numeroTransacaoPagamento = numeroTransacaoPagamento;
	}

}
