package br.com.shopdosmusicos.controller.schema.geral;

public record EnumValorDescricaoResponse(
        Object value,
        String label,
        String descricao
) {
}
