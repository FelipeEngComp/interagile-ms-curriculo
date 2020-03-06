package com.interagile.cliente.escola.model;

import java.util.List;

import lombok.Value;

@Value
public class CursoCadastroModel {
	private final String codigo;
	private final String nome;
	private final int semestres;
	private final List<String> materias;
}
