package com.interagile.cliente.escola.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.interagile.cliente.escola.dao.CursoDAO;
import com.interagile.cliente.escola.dao.MateriaDAO;
import com.interagile.cliente.escola.exception.CurriculoException;
import com.interagile.cliente.escola.model.CursoCadastroModel;
import com.interagile.cliente.escola.repository.ICursoRepository;

@Service
public class CursoService implements ICursoService {

	private ICursoRepository cursoRepository;
	private IMateriaService materiaService;

	@Autowired
	public CursoService(ICursoRepository cursoRepository, IMateriaService materiaService) {
		this.cursoRepository = cursoRepository;
		this.materiaService = materiaService;
	}

	@Override
	public Boolean cadastra(CursoCadastroModel curso) {
		try {

			if (this.cursoRepository.findCursoByCodigo(StringUtils.upperCase(curso.getCodigo())) != null) {
				throw new CurriculoException("Curso já cadastrado", HttpStatus.BAD_REQUEST.value());
			}

			this.validarParametrosDeEntrada(curso);

			CursoDAO cursoDAO = this.criaObj(curso, new CursoDAO());

			this.cursoRepository.save(cursoDAO);

			return true;

		} catch (CurriculoException curriculoException) {
			throw curriculoException;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public CursoDAO consultaPorCod(String codigo) {

		CursoDAO curso = this.cursoRepository.findCursoByCodigo(codigo);

		if (curso == null) {
			throw new CurriculoException("Curso não encontrado", HttpStatus.BAD_REQUEST.value());
		}

		return curso;
	}

	@Override
	public Boolean atualizar(CursoCadastroModel cursoModel) {

		this.validarParametrosDeEntrada(cursoModel);

		CursoDAO curso = this.cursoRepository.findCursoByCodigo(StringUtils.upperCase(cursoModel.getCodigo()));

		if (curso == null) {
			throw new CurriculoException("Curso ainda não cadastrado", HttpStatus.BAD_REQUEST.value());
		}

		this.cursoRepository.save(this.criaObj(cursoModel, curso));

		return true;
	}

	@Override
	public List<CursoDAO> listarCursos() {

		List<CursoDAO> cursoDao = this.cursoRepository.findAll();
		return cursoDao;
	}

	private List<MateriaDAO> consultaMaterias(List<String> materias) {

		List<MateriaDAO> materiaDao = new ArrayList<>();

		if (materias != null)
			for (String materia : materias) {
				materiaDao.add(this.materiaService.consultarMateriaDaoCadastrada(StringUtils.upperCase(materia)));
			}
		return materiaDao;
	}

	private CursoDAO criaObj(CursoCadastroModel curso, CursoDAO cursoDAO) {

		List<MateriaDAO> materias = this.consultaMaterias(curso.getMaterias());

		cursoDAO.setCodigo(StringUtils.upperCase(curso.getCodigo()));
		cursoDAO.setNome(StringUtils.upperCase(curso.getNome()));
		cursoDAO.setSemestres(curso.getSemestres());
		cursoDAO.setMaterias(materias);
		return cursoDAO;
	}

	private void validarParametrosDeEntrada(CursoCadastroModel curso) {

		if (curso.getSemestres() <= 0 || curso.getSemestres() >= 15) {
			throw new CurriculoException("Quantidade de semestres incorretas", HttpStatus.BAD_REQUEST.value());
		}

		if (curso.getNome() == null || StringUtils.isEmpty(curso.getNome())) {
			throw new CurriculoException("Nome do curso incorreto", HttpStatus.BAD_REQUEST.value());
		}
	}
}
