package com.interagile.cliente.escola.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.interagile.cliente.escola.dao.CursoDAO;
import com.interagile.cliente.escola.dao.MateriaDAO;
import com.interagile.cliente.escola.exception.CurriculoException;
import com.interagile.cliente.escola.model.MateriaCadastroModel;
import com.interagile.cliente.escola.repository.IMateriaRepository;

@Service
public class MateriaService implements IMateriaService {

	private IMateriaRepository materiaRepository;

	@Autowired
	public MateriaService(IMateriaRepository materiaRepository) {
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Boolean cadastrar(final MateriaCadastroModel materia) {

		try {
			this.validaCadastro(materia);
			MateriaDAO materiaDao = new MateriaDAO();
			materiaDao.setNome(StringUtils.upperCase(materia.getNome()));
			materiaDao.setCodigo(StringUtils.upperCase(materia.getCodigo()));
			materiaDao.setFrequencia(materia.getFrequencia());
			materiaDao.setHoras(materia.getHoras());

			this.materiaRepository.save(materiaDao);
			return true;
		} catch (CurriculoException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public MateriaCadastroModel consultarMateriaCadastrada(String codMateria) {
		try {
			final MateriaDAO materiaDao = this.materiaRepository.findMateriaByCodigo(codMateria);
			if (materiaDao == null) {
				throw new CurriculoException("Matéria não encontrada", HttpStatus.BAD_REQUEST.value());
			}
			MateriaCadastroModel materia = new MateriaCadastroModel() ;
			materia.setCodigo(materiaDao.getCodigo());
			materia.setFrequencia(materiaDao.getFrequencia());
			materia.setHoras(materiaDao.getHoras());
			materia.setNome(materiaDao.getNome());

			return materia;
		} catch (CurriculoException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Boolean atualizar(MateriaCadastroModel materia) {
		try {
			this.validaEntradaGeral(materia);
			MateriaDAO materiaDao = this.materiaRepository.findMateriaByCodigo(materia.getCodigo());
			if (materiaDao != null) {
				materiaDao.setIdMateria(materiaDao.getIdMateria());
				materiaDao.setNome(StringUtils.upperCase(materia.getNome()));
				materiaDao.setCodigo(StringUtils.upperCase(materia.getCodigo()));
				materiaDao.setFrequencia(materia.getFrequencia());
				materiaDao.setHoras(materia.getHoras());

				this.materiaRepository.save(materiaDao);

				return true;
			}
			throw new CurriculoException("Matéria inexistente", HttpStatus.BAD_REQUEST.value());
		} catch (CurriculoException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<MateriaDAO> listarMaterias() {
		return this.materiaRepository.findAll();
	}

	public MateriaDAO consultarMateriaDaoCadastrada(String codMateria) {
		try {
			final MateriaDAO materiaDao = this.materiaRepository.findMateriaByCodigo(codMateria);
			if (materiaDao == null) {
				throw new CurriculoException("Matéria não encontrada", HttpStatus.BAD_REQUEST.value());
			}

			return materiaDao;
		} catch (CurriculoException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}

	}

	private void validaCadastro(MateriaCadastroModel materia) {
		if (this.materiaRepository.findMateriaByCodigo(StringUtils.upperCase(materia.getCodigo())) != null) {
			throw new CurriculoException("Código já foi usado para cadastro de outra matéria",
					HttpStatus.BAD_REQUEST.value());
		}
		this.validaEntradaGeral(materia);
	}

	private void validaEntradaGeral(MateriaCadastroModel materia) {
		if (materia.getNome() == null || StringUtils.isEmpty(materia.getNome())) {
			throw new CurriculoException("Nome da matéria vazio.", HttpStatus.BAD_REQUEST.value());
		}
		if (materia.getCodigo() == null || StringUtils.isEmpty(materia.getCodigo())) {
			throw new CurriculoException("Código da matéria vazio.", HttpStatus.BAD_REQUEST.value());
		}
		if (materia.getHoras() <= 0) {
			throw new CurriculoException("Horas da máteria têm que ser positivas.", HttpStatus.BAD_REQUEST.value());
		}
		if (materia.getFrequencia() != 1 && materia.getFrequencia() != 2) {
			throw new CurriculoException("Frequência pode ser apenas 1 ou 2.", HttpStatus.BAD_REQUEST.value());
		}
	}

}
