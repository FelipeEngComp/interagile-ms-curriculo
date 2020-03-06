package com.interagile.cliente.escola.service;

import com.interagile.cliente.escola.dao.CursoDAO;
import com.interagile.cliente.escola.model.CursoCadastroModel;

public interface ICursoService {
	
	public Boolean cadastra(final CursoCadastroModel curso);
	
	public CursoDAO consultaPorCod(final String codigo);
	
	public Boolean atualizar(final CursoDAO curso);
}
