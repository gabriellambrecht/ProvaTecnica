package com.example.votacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.votacao.exception.ValidacaoException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({ ValidacaoException.class })
	public ResponseEntity<String> validacaoException(ValidacaoException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}