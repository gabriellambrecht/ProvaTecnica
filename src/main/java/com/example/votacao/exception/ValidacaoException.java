package com.example.votacao.exception;

public class ValidacaoException extends Exception { 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidacaoException(String errorMessage) {
        super(errorMessage);
    }
}