package com.interagile.cliente.escola.model;

import lombok.Data;
import lombok.Value;

@Data
public class MateriaCadastroModel{
	
	private  String nome;
	private  int horas;
	private  String codigo;
	private  int frequencia;
	
}
