package br.com.mercadoinstrumental.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;



public class ErrorResponseBuilder {

    public static final String HEADER_INTERNAL_ERROR_TYPE = "X-Internal-Error-Type";
    public static final String HEADER_AMZFW_VERSION = "X-Internal-Amzfw-Main-Version";
    
    public enum InternalErrorType {
        ERROR, VALIDATION
    }
    
	private final HttpStatus httpStatus;
	private String message;
	private String details;
    private String code;
    private String path;
    private String trace;
    private boolean generateId = true;
	private final ResponseEntity.BodyBuilder bodyBuilder;
	private InternalErrorType errorType = InternalErrorType.ERROR;
    private List<ErrorResponseItem> errorResponseItems;
    
	private ErrorResponseBuilder(HttpStatus httpStatus, String message, String details) {
		this.httpStatus = httpStatus;
		this.message = message;
		this.details = details;
		this.bodyBuilder = ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON);
	}

	public static ErrorResponseBuilder create(HttpStatus status) {
		return new ErrorResponseBuilder(status, status.getReasonPhrase(), null);
	}

    public ErrorResponseBuilder message(String message) {
        this.message = message;
        return this;
    }
    
    public ErrorResponseBuilder code(String code) {
        this.code = code;
        return this;
    }

    public ErrorResponseBuilder trace(Throwable exception) {
        if (exception != null) {
            StringWriter stackTrace = new StringWriter();
            exception.printStackTrace(new PrintWriter(stackTrace));
            stackTrace.flush();
            this.trace = stackTrace.toString();
        }
        return this;
    }

    
    public ErrorResponseBuilder path(String path) {
        this.path = path;
        return this;
    }
    
    public ResponseEntity<ErrorResponse> build() {
        ErrorResponse error = new ErrorResponse(generateId ? UUID.randomUUID().toString() : null,
                ZonedDateTime.now(ZoneOffset.UTC), httpStatus.value(), message, details, code, path, errorResponseItems, trace);
        return this.bodyBuilder
                .header(HEADER_INTERNAL_ERROR_TYPE, errorType.name())
                .header(HEADER_AMZFW_VERSION, "4")
                .body(error);
    }
    

}
