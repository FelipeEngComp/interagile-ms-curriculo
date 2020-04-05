package com.interagile.cliente.escola.service;

import java.util.List;

import com.interagile.cliente.escola.model.CursoCadastroModel;
import com.interagile.cliente.escola.model.dto.CursoDTO;

public interface ICursoService {
	
	public Boolean cadastra(final CursoCadastroModel curso);
	
	public CursoDTO consultaPorCod(final String codigo);
	
	public Boolean atualizar(final CursoCadastroModel cursoModel);
	
	public List<CursoDTO> listarCursos();
}
