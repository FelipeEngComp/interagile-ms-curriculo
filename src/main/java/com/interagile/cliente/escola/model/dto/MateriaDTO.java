package com.interagile.cliente.escola.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MateriaDTO implements Serializable {

	public MateriaDTO() {
		
	}
	
	private static final long serialVersionUID = 1333250614697818519L;

	private Long idMateria;

	private String nome;

	private int horas;

	private String codigo;

	private int frequencia;

	private boolean cursada;

}
