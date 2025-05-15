package br.com.mercadoinstrumental.exceptions;


import jakarta.validation.constraints.NotBlank;

public record ErrorResponseItem(

        @NotBlank
        String item,

        @NotBlank
        String reason,

        Object rejectedValue
) {
}
