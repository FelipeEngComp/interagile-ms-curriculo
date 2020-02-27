package com.interagile.cliente.escola.service;

import com.interagile.cliente.escola.model.MateriaCadastroModel;

public interface IMateriaService {
	
	Boolean cadastrar(final MateriaCadastroModel materia);
	
	MateriaCadastroModel consultarMateriaCadastrada(final String codMateria);
	
	Boolean atualizar(final MateriaCadastroModel materia);
	
}
