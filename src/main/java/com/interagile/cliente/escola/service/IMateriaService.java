package com.interagile.cliente.escola.service;

import java.util.List;

import com.interagile.cliente.escola.model.MateriaCadastroModel;

public interface IMateriaService {
	
	Boolean cadastrar(final MateriaCadastroModel materia);
	
	MateriaCadastroModel consultarMateriaCadastrada(final String codMateria);
	
	Boolean atualizar(final MateriaCadastroModel materia);
	
	Boolean excluirMaterias(final List<String>codigosMaterias);
}
