package com.interagile.cliente.escola.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class MateriaCadastroModel {
	
	private final String nome;
	private final int horas;
	private final String codigo;
	private final int frequencia;
	
}
