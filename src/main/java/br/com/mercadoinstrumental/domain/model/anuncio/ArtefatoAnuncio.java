package br.com.mercadoinstrumental.domain.model.anuncio;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ARTEFATO_ANUNCIO")
public class ArtefatoAnuncio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ARTEFATO_ANUNCIO")
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_ANUNCIO")
	private Anuncio anuncio;

	@NotNull
	@Column(name = "NU_ARTEFATO")
	private Integer numero;

	@Column(name = "SRC_DIR")
	private String srcDocumento;

	@NotNull
	@Column(name = "IN_MINIATURA", nullable = false)
	private Boolean miniatura;

	@Column(name = "DH_UPLOAD")
	private LocalDateTime dataHoraUpload;

	public ArtefatoAnuncio() {}
	
	public ArtefatoAnuncio(@NotNull Anuncio anuncio, @NotNull String srcDocumento, @NotNull Boolean miniatura) {
		this.anuncio = anuncio;
		this.srcDocumento = srcDocumento;
		this.miniatura = miniatura;
		this.dataHoraUpload = LocalDateTime.now();
	}

	public ArtefatoAnuncio(Anuncio anuncio, Boolean miniatura, Integer numero) {
		this.anuncio = anuncio;
		this.miniatura = miniatura;
		this.numero = numero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Anuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}

	public String getSrcDocumento() {
		return srcDocumento;
	}

	public void setSrcDocumento(String srcDocumento) {
		this.srcDocumento = srcDocumento;
	}

	public Boolean getMiniatura() {
		return miniatura;
	}

	public void setMiniatura(Boolean miniatura) {
		this.miniatura = miniatura;
	}

	public LocalDateTime getDataHoraUpload() {
		return dataHoraUpload;
	}

	public void setDataHoraUpload(LocalDateTime dataHoraUpload) {
		this.dataHoraUpload = dataHoraUpload;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	
}
