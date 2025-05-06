package br.com.mercadoinstrumental.controller.schema.geral;

public record EnumValorSituacaoResponse(
        Object value,
        String label,
        Boolean situacao
) {
}
