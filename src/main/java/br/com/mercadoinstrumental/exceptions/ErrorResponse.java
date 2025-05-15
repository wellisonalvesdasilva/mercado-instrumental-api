package br.com.mercadoinstrumental.exceptions;


import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ErrorResponse(

        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        String id,

        @NotNull
        ZonedDateTime timestamp,

       
        int status,

        String message,

        @NotBlank
        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        String details,

        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        String code,

        @NotBlank
        String path,

        @JsonInclude(JsonInclude.Include.NON_ABSENT) List<ErrorResponseItem> errors,

        @JsonInclude(JsonInclude.Include.NON_ABSENT) String trace

) {

}
