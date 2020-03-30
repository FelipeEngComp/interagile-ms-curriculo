package com.interagile.cliente.escola.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
@JsonDeserialize
public class MateriaCadastroModel{
	
	private final String nome;
	private final int horas;
	private final String codigo;
	private final int frequencia;
	
}
