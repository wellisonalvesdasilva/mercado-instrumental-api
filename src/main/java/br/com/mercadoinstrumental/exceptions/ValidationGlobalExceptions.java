package br.com.mercadoinstrumental.exceptions;

import static org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeAttribute.ALWAYS;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ValidationGlobalExceptions {

    private final ErrorProperties errorProperties;

    public ValidationGlobalExceptions(ServerProperties serverProperties) {
        this.errorProperties = serverProperties.getError();
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e, HttpServletRequest servletRequest) {
        return ErrorResponseBuilder.create(UNPROCESSABLE_ENTITY)
                .message(e.getMessage())
                .code(e.getMessageCode())
                .trace(ALWAYS.equals(errorProperties.getIncludeStacktrace()) ? e : null)
                .path(servletRequest.getRequestURI())
                .build();
    }
}
