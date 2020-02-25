package com.interagile.cliente.escola.exception;

import lombok.Getter;

@Getter
public class MateriaException extends RuntimeException{
	
	private static final long serialVersionUID = 8349107845092148544L;
	private final int httpStatusCode;
	
	public MateriaException(final String mensagem,final int httpStatusCode) {
		super(mensagem);
		this.httpStatusCode = httpStatusCode;
	}
}
