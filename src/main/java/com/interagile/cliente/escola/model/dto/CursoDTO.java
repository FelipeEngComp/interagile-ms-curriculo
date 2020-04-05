package com.interagile.cliente.escola.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.interagile.cliente.escola.dao.MateriaDAO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CursoDTO implements Serializable{
	
	private static final long serialVersionUID = -5203036138487050071L;

	public CursoDTO() {
	}

	private long id;

	private String codigo;

	private String nome;

	private int semestres;

	private List<MateriaDAO> materias = new ArrayList<>();
}
