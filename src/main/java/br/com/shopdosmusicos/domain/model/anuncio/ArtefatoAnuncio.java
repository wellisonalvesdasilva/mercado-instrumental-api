package br.com.shopdosmusicos.domain.model.anuncio;

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
	@Lob
	@Column(name = "SRC_DIR")
	private String srcDocumento;

	@NotNull
	@Column(name = "IN_MINIATURA", nullable = false)
	private Boolean miniatura;

	@NotNull
	@Column(name = "DH_UPLOAD")
	private LocalDateTime dataHoraUpload;

	public ArtefatoAnuncio(@NotNull Anuncio anuncio, @NotNull String srcDocumento, @NotNull Boolean miniatura) {
		this.anuncio = anuncio;
		this.srcDocumento = srcDocumento;
		this.miniatura = miniatura;
		this.dataHoraUpload = LocalDateTime.now();
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

}
