package com.interagile.cliente.escola.service;

import java.util.List;

import com.interagile.cliente.escola.dao.CursoDAO;
import com.interagile.cliente.escola.model.CursoCadastroModel;

public interface ICursoService {
	
	public Boolean cadastra(final CursoCadastroModel curso);
	
	public CursoDAO consultaPorCod(final String codigo);
	
	public Boolean atualizar(final CursoCadastroModel cursoModel);
	
	public List<CursoDAO> listarCursos();
}
