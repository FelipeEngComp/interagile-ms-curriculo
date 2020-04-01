package com.interagile.cliente.escola.model;

import java.util.List;

import lombok.Data;

@Data
public class CursoCadastroModel {

	private  String codigo;
	private  String nome;
	private  int semestres;
	private  List<String> materias;

}
