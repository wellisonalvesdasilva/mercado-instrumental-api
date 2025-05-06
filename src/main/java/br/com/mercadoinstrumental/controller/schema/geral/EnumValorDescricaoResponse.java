package br.com.mercadoinstrumental.controller.schema.geral;

public record EnumValorDescricaoResponse(
        Object value,
        String label,
        String descricao
) {
}
