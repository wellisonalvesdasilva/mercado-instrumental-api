package br.com.shopdosmusicos.controller.admin.anuncio.schema;

import java.math.BigDecimal;

import br.com.shopdosmusicos.domain.model.anuncio.MarcaInstrumentoMusical;
import br.com.shopdosmusicos.domain.model.anuncio.TipoInstrumentoMusicalEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record AnuncioReq(
    
    @NotNull
    Long id,
    
    @NotNull
    @Size(min = 1, max = 100)
    String nome,
    
    @NotNull
    @Size(min = 1, max = 4000)
    String descricao,
    
    @NotNull
    TipoInstrumentoMusicalEnum tipo,
    
    @NotNull
    MarcaInstrumentoMusical marca,
    
    @NotNull
    Long idMunicipioIbge,
    
    @NotNull
    @Min(value = 0)
    BigDecimal valor,
    
    @NotNull
    Boolean novo
) {
    
}
