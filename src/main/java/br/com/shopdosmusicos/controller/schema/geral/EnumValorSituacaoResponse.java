package br.com.shopdosmusicos.controller.schema.geral;

public record EnumValorSituacaoResponse(
        Object value,
        String label,
        Boolean situacao
) {
}
