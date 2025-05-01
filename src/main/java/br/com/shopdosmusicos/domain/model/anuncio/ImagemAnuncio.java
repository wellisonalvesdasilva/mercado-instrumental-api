package br.com.shopdosmusicos.domain.model.anuncio;

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
@Table(name = "IMAGEM_ANUNCIO")
public class ImagemAnuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_IMAGEM_ANUNCIO")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_ANUNCIO")
    private Anuncio anuncio;

    @NotNull
    @Lob
    @Column(name = "CONTEUDO_ARQUIVO")
    private byte[] conteudoArquivo;

    @NotNull
    @Column(name = "IN_MINIATURA", nullable = false)
    private Boolean miniatura;

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

    public byte[] getConteudoArquivo() {
        return conteudoArquivo;
    }

    public void setConteudoArquivo(byte[] conteudoArquivo) {
        this.conteudoArquivo = conteudoArquivo;
    }

    public Boolean getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(Boolean miniatura) {
        this.miniatura = miniatura;
    }
}
