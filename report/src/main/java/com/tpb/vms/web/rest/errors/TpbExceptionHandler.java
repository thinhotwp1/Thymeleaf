/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.web.rest.errors;

import java.util.Objects;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Viet Do-Van
 */
@ControllerAdvice
public class TpbExceptionHandler extends ResponseEntityExceptionHandler {

	private final MessageProvider messageProvider;

	public TpbExceptionHandler(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.error(ex.toString(), ex);
		FieldError fieldError = null;
		ObjectError objectError = null;
		BindingResult bindingResult = ex.getBindingResult();
		if (bindingResult != null) {
			fieldError = bindingResult.getFieldError();
			objectError = bindingResult.getGlobalError();
		}
		String field = null;
		String defaultMessage = null;

		if (Objects.nonNull(objectError)) {
			field = objectError.getObjectName();
			defaultMessage = objectError.getDefaultMessage();
		} else if (Objects.nonNull(fieldError)) {
			field = fieldError.getField();
			defaultMessage = fieldError.getDefaultMessage();
		}

		StringBuilder message = new StringBuilder();
		String errorStatus = ErrorProvider.VALIDATION_ERROR.getStatus();
		if (field != null && defaultMessage != null) {
			message.append("[").append(field).append("]").append(" - ")
					.append(this.messageProvider.getMessage(defaultMessage));
			errorStatus = ErrorProvider.getByCode(defaultMessage).getStatus();
		} else {
			message.append("REQUEST_PARAMS_INVALID");
		}
		return handleExceptionInternal(ex, ApiError.builder().status(errorStatus).message(message.toString()).build(),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class })
	protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
		logger.error("409 status code",ex);
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
	public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
		logger.error("500 Status Code", ex);
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity<Object> handleAuthentication(final RuntimeException ex, final WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDenied(final RuntimeException ex, final WebRequest request) {
		logger.error(ex.toString(), ex);
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

	@ExceptionHandler({ VmsException.class })
	public ResponseEntity<Object> handleInterestRateException(final VmsException ex,
			final WebRequest request) {
		logger.error(ex.toString(), ex);
		String message;
		if (StringUtils.isEmpty(ex.getCode())) {
			message = ex.getMessage();
		} else {
			if (ex.getListParams() != null) {
				message = String.format(this.messageProvider.getMessage(ex.getCode()), ex.getListParams().toArray());
			} else {
				message=this.messageProvider.getMessage(ex.getCode());
			}
		}
		return handleExceptionInternal(ex, ApiError.builder().status(ex.getStatus()).message(message).build(),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	

	@ExceptionHandler({ BulkApiException.class })
	public ResponseEntity<Object> handleAccessDenied(final BulkApiException ex, final WebRequest request) {
		logger.error(ex.toString(), ex);
		return handleExceptionInternal(ex,
				BulkApiError.builder().status(ex.getStatus())
						.message(StringUtils.isEmpty(ex.getCode()) ? ex.getMessage()
								: this.messageProvider.getMessage(ex.getCode()))
						.errors(ex.getErrors()).otherErrors(ex.getOtherErrors()).build(),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		logger.error(ex.toString(), ex);
		return handleExceptionInternal(ex,
				ApiError.builder().status(ErrorProvider.INTERNAL_SERVER_ERROR.getStatus())
						.message(ErrorProvider.INTERNAL_SERVER_ERROR.getMessage()).build(),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		logger.error(ex.toString(), ex);
		FieldError fieldError = null;
		BindingResult bindingResult = ex.getBindingResult();
		if (bindingResult != null) {
			fieldError = ex.getBindingResult().getFieldError();
		}
		StringBuilder message = new StringBuilder();
		String errorStatus = ErrorProvider.VALIDATION_ERROR.getStatus();
		if (fieldError != null) {
			message.append("[").append(fieldError.getField()).append("]").append(" - ")
					.append(this.messageProvider.getMessage(fieldError.getDefaultMessage()));
			errorStatus = ErrorProvider.getByCode(fieldError.getDefaultMessage()).getStatus();
		} else {
			message.append("REQUEST_PARAMS_INVALID");
		}
		return handleExceptionInternal(ex, ApiError.builder().status(errorStatus).message(message.toString()).build(),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	final String bodyOfResponse = "This should be application specific";
}
