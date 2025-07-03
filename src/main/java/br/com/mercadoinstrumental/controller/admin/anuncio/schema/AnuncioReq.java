package br.com.mercadoinstrumental.controller.admin.anuncio.schema;

import java.math.BigDecimal;

import br.com.mercadoinstrumental.domain.model.anuncio.MarcaInstrumentoMusicalEnum;
import br.com.mercadoinstrumental.domain.model.anuncio.TipoInstrumentoMusicalEnum;
import br.com.mercadoinstrumental.domain.model.anuncio.TipoPlanoEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record AnuncioReq(
    
    @NotNull
    @Size(min = 1, max = 100)
    String titulo,
    
    @NotNull
    @Size(min = 1, max = 4000)
    String descricao,
    
    @NotNull
    TipoInstrumentoMusicalEnum tipo,
    
    @NotNull
    MarcaInstrumentoMusicalEnum marca,
    
    @NotNull
    TipoPlanoEnum tipoPlano,
    
    @NotNull
    String estado,
    
    @NotNull
    String municipio,
    
    @NotNull
    @Min(value = 0)
    BigDecimal valor,
    
    @NotNull
    Boolean novo
) {
    
}
