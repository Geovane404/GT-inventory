package com.gtecnologia.GTinventory.controllers.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gtecnologia.GTinventory.services.exception.DatabaseException;
import com.gtecnologia.GTinventory.services.exception.ForbiddenException;
import com.gtecnologia.GTinventory.services.exception.ResourceNotFoundException;
import com.gtecnologia.GTinventory.services.exception.UnauthorizedException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(HttpServletRequest request, ResourceNotFoundException e) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError();

		err.setTimesTamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Recurso não encontrado!");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(HttpServletRequest request, DatabaseException e) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError();

		err.setTimesTamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Exceção do banco de dados");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(HttpServletRequest request, MethodArgumentNotValidException e) {

		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError err = new ValidationError();

		err.setTimesTamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Validation exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		for(FieldError error : e.getBindingResult().getFieldErrors()) {
			
			err.addErrror(error.getField(), error.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<OAuthCustomError> forbidden(ForbiddenException e) {

		HttpStatus status = HttpStatus.FORBIDDEN;
		OAuthCustomError err = new OAuthCustomError("Forbidden", e.getMessage());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<OAuthCustomError> unauthorized(UnauthorizedException e) {

		HttpStatus status = HttpStatus.UNAUTHORIZED;
		OAuthCustomError err = new OAuthCustomError("Unauthorized", e.getMessage());

		return ResponseEntity.status(status).body(err);
	}
}
