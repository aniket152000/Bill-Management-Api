package com.example.billmanagementapi.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.billmanagementapi.entity.ErrorMessage;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleBillsNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {

		ErrorMessage errorObject = new ErrorMessage();

		errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());

		errorObject.setMessage(ex.getMessage());

		errorObject.setTimestamp(new Date());

		return new ResponseEntity<ErrorMessage>(errorObject, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorMessage> handleMethodArgumentMismatchException(MethodArgumentTypeMismatchException ex,
			WebRequest request) {

		ErrorMessage errorObject = new ErrorMessage();

		errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());

		errorObject.setMessage(ex.getMessage());

		errorObject.setTimestamp(new Date());

		return new ResponseEntity<ErrorMessage>(errorObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex, WebRequest request) {

		ErrorMessage errorObject = new ErrorMessage();

		errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		errorObject.setMessage(ex.getMessage());

		errorObject.setTimestamp(new Date());

		return new ResponseEntity<ErrorMessage>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new HashMap<String, Object>();

		body.put("statusCode", HttpStatus.BAD_REQUEST.value());

		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("messages", errors);

		body.put("timestamp", new Date());

		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);

	}

}
