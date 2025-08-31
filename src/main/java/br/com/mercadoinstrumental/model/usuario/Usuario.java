package br.com.mercadoinstrumental.model.usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.domain.model.anuncio.TipoInstrumentoMusicalEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 100)
	@Column(name = "NO_USUARIO")
	private String nome;

	@NotNull
	@Column(name = "TX_EMAIL", unique = true)
	private String email;

	@NotNull
	@Column(name = "NU_WHATSAPP")
	private String whats;

	@JsonIgnore
	@Column(name = "TX_SENHA")
	private String senha;

	@Column(name = "TX_PAL_CHAVE_TEMP")
	private String palavraChaveTemp;

	@Column(name = "IN_ATIVO")
	private Boolean ativo;

	@Column(name = "IN_HAS_ANUNCIO_FREE")
	private Boolean teveAnuncioGratis;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "USUARIO_PERFIL")
	private Set<Integer> perfil = new HashSet<>();

	@NotNull
	@Column(name = "TX_HASH_PROPRIA", unique = true)
	private String hashPropria;

	@Column(name = "TX_HASH_DE_QUEM_INDICOU")
	private String hashDeQuemIndicou;

	@NotNull
	@Column(name = "DT_CADASTRO")
	private LocalDate dataCadastro;

	@Enumerated(EnumType.STRING)
	@Column(name = "EN_TIPO_CHAVE_PIX")
	private TipoChavePixEnum tipoChavePix;

	@Column(name = "CHAVE_PIX")
	private String chavePix;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Anuncio> anuncios = new HashSet<>();

	public Usuario() {
	}

	public Usuario(String nome, String email, String senha, String whats) {
		this.nome = nome;
		this.email = email;
		this.senha = new BCryptPasswordEncoder().encode(senha);
		this.whats = whats;
		addPerfil(TipoPerfilEnum.ANUNCIANTE);
		gerarNovaSenhaTemporaria();
		ativo = false;
		teveAnuncioGratis = false;
		this.dataCadastro = LocalDate.now();
	}

	public TipoChavePixEnum getTipoChavePix() {
		return tipoChavePix;
	}

	public void setTipoChavePix(TipoChavePixEnum tipoChavePix) {
		this.tipoChavePix = tipoChavePix;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = new BCryptPasswordEncoder().encode(senha);
	}

	public Set<TipoPerfilEnum> getPerfis() {
		return perfil.stream().map(TipoPerfilEnum::toEnum).collect(Collectors.toSet());
	}

	public void addPerfil(TipoPerfilEnum item) {
		perfil.add(item.getCod());
	}

	public Set<Integer> getListaPerfis() {
		return perfil;
	}

	public String getChavePix() {
		return chavePix;
	}

	public void setChavePix(String chavePix) {
		this.chavePix = chavePix;
	}

	public void setPerfil(Set<Integer> listaPerfis) {
		this.perfil = listaPerfis;
	}

	public String getWhats() {
		return whats;
	}

	public void setWhats(String whats) {
		this.whats = whats;
	}

	public Set<Integer> getPerfil() {
		return perfil;
	}

	public String getPalavraChaveTemp() {
		return palavraChaveTemp;
	}

	public void setPalavraChaveTemp(String palavraChaveTemp) {
		this.palavraChaveTemp = palavraChaveTemp;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public void gerarNovaSenhaTemporaria() {
		this.palavraChaveTemp = new Random().ints(6, 0, 10).mapToObj(String::valueOf).collect(Collectors.joining());
	}

	public Boolean getTeveAnuncioGratis() {
		return teveAnuncioGratis;
	}

	public void setTeveAnuncioGratis(Boolean teveAnuncioGratis) {
		this.teveAnuncioGratis = teveAnuncioGratis;
	}

	public String getHashPropria() {
		return hashPropria;
	}

	public void setHashPropria(String hashPropria) {
		this.hashPropria = hashPropria;
	}

	public String getHashDeQuemIndicou() {
		return hashDeQuemIndicou;
	}

	public void setHashDeQuemIndicou(String hashDeQuemIndicou) {
		this.hashDeQuemIndicou = hashDeQuemIndicou;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Set<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(Set<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
