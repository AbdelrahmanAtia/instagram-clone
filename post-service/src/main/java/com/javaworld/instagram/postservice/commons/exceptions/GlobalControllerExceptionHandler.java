package com.javaworld.instagram.postservice.commons.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.javaworld.instagram.commonlib.exception.BadRequestException;
import com.javaworld.instagram.commonlib.exception.HttpErrorInfo;
import com.javaworld.instagram.commonlib.exception.InvalidInputException;
import com.javaworld.instagram.commonlib.exception.NotFoundException;

@RestControllerAdvice
class GlobalControllerExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

	/**
	 * most of the bad requests  are handled by swagger..and this function will
	 * handle other bad requests that are thrown after the request enters the 
	 * RestImpl layer
	 */
	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public @ResponseBody HttpErrorInfo handleBadRequestExceptions(HttpServletRequest request, BadRequestException ex) {

		return createHttpErrorInfo(BAD_REQUEST, request, ex);
	}

	@ResponseStatus(NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public @ResponseBody HttpErrorInfo handleNotFoundExceptions(HttpServletRequest request, NotFoundException ex) {

		return createHttpErrorInfo(NOT_FOUND, request, ex);
	}

	@ResponseStatus(UNPROCESSABLE_ENTITY)
	@ExceptionHandler(InvalidInputException.class)
	public @ResponseBody HttpErrorInfo handleInvalidInputException(HttpServletRequest request,
			InvalidInputException ex) {

		return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
	}
	
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ExceptionHandler(TimeoutException.class)
	public @ResponseBody HttpErrorInfo handleTimeoutException(HttpServletRequest request, TimeoutException ex) {
		return createHttpErrorInfo(INTERNAL_SERVER_ERROR, request, ex);
	}

	private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, HttpServletRequest request, Exception ex) {

		// final String path = request.getPath().pathWithinApplication().value();
		final String path = request.getServletPath();
		final String message = ex.getMessage();

		LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
		return new HttpErrorInfo(httpStatus, path, message);
	}
}