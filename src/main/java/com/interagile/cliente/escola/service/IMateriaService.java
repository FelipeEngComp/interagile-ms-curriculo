package com.interagile.cliente.escola.service;

import java.util.List;

import com.interagile.cliente.escola.model.MateriaCadastroModel;
import com.interagile.cliente.escola.model.MateriaDB;
import com.interagile.cliente.escola.model.dto.MateriaDTO;

public interface IMateriaService {
	
	public Boolean cadastrar(final MateriaCadastroModel materia);
	
	public MateriaDTO consultarMateriaCadastrada(final String codMateria);
	
	public Boolean atualizar(final MateriaCadastroModel materia);
	
	public MateriaDB consultarMateriaDaoCadastrada(String codMateria);

	public List<MateriaDTO> listar();
	
}
