package br.com.mercadoinstrumental.exceptions;

import java.util.function.Supplier;

public class BusinessException extends RuntimeException {

	private final String messageCode;

	public BusinessException(String message) {
		super(message);
		this.messageCode = null;
	}

	public BusinessException(String messageCode, String message) {
		super(message);
		this.messageCode = messageCode;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.messageCode = null;
	}

	public BusinessException(String messageCode, String message, Throwable cause) {
		super(message, cause);
		this.messageCode = messageCode;
	}

	protected BusinessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.messageCode = null;
	}

	public static Supplier<BusinessException> from(String messageCode, String message) {
		return () -> new BusinessException(messageCode, message);
	}

	public static Supplier<BusinessException> from(String message) {
		return () -> new BusinessException(null, message);
	}

	public String getMessageCode() {
		return messageCode;
	}
}
