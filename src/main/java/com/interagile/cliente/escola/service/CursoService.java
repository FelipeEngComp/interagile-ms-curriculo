package com.interagile.cliente.escola.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interagile.cliente.escola.exception.CurriculoException;
import com.interagile.cliente.escola.model.CursoCadastroModel;
import com.interagile.cliente.escola.model.CursoDB;
import com.interagile.cliente.escola.model.MateriaDB;
import com.interagile.cliente.escola.model.dto.CursoDTO;
import com.interagile.cliente.escola.repository.ICursoRepository;

@Service
public class CursoService implements ICursoService {

	private ICursoRepository cursoRepository;
	private IMateriaService materiaService;
	private ObjectMapper mapper;

	@Autowired
	public CursoService(ICursoRepository cursoRepository, IMateriaService materiaService) {
		this.cursoRepository = cursoRepository;
		this.materiaService = materiaService;
		this.mapper = new ObjectMapper();
	}

	@Override
	public Boolean cadastra(CursoCadastroModel curso) {
		try {

			if (this.cursoRepository.findCursoByCodigo(StringUtils.upperCase(curso.getCodigo())) != null) {
				throw new CurriculoException("Curso já cadastrado", HttpStatus.BAD_REQUEST.value());
			}

			this.validarParametrosDeEntrada(curso);

			CursoDB cursoDAO = this.criaObj(curso, new CursoDB());

			this.cursoRepository.save(cursoDAO);

			return true;

		} catch (CurriculoException curriculoException) {
			throw curriculoException;
		} catch (Exception e) {
			throw e;
		}

	}

	@Cacheable(value = "consultar-curso", key = "#codigo", unless = "#result == null")
	@Override
	public CursoDTO consultaPorCod(String codigo) {

		CursoDB curso = this.cursoRepository.findCursoByCodigo(codigo);

		if (curso == null) {
			throw new CurriculoException("Curso não encontrado", HttpStatus.BAD_REQUEST.value());
		}

		return this.mapper.convertValue(curso, CursoDTO.class);
	}

	@Override
	public Boolean atualizar(CursoCadastroModel cursoModel) {

		this.validarParametrosDeEntrada(cursoModel);

		CursoDB curso = this.cursoRepository.findCursoByCodigo(StringUtils.upperCase(cursoModel.getCodigo()));

		if (curso == null) {
			throw new CurriculoException("Curso ainda não cadastrado", HttpStatus.BAD_REQUEST.value());
		}

		this.cursoRepository.save(this.criaObj(cursoModel, curso));

		return true;
	}

	@Override
	public List<CursoDTO> listarCursos() {

		try {
			List<CursoDB> cursoDao = this.cursoRepository.findAll();

			return this.mapper.convertValue(cursoDao, new TypeReference<List<CursoDTO>>() {
			});
		} catch (Exception e) {
			throw e;
		}
	}

	private List<MateriaDB> consultaMaterias(List<String> materias) {

		List<MateriaDB> materiaDao = new ArrayList<>();

		if (materias != null)
			for (String materia : materias) {
				materiaDao.add(this.materiaService.consultarMateriaDaoCadastrada(StringUtils.upperCase(materia)));
			}
		return materiaDao;
	}

	private CursoDB criaObj(CursoCadastroModel curso, CursoDB cursoDAO) {

		List<MateriaDB> materias = this.consultaMaterias(curso.getMaterias());

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
