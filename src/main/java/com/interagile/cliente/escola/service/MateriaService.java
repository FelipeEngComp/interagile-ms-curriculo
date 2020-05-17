package com.interagile.cliente.escola.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interagile.cliente.escola.exception.CurriculoException;
import com.interagile.cliente.escola.model.MateriaCadastroModel;
import com.interagile.cliente.escola.model.MateriaDB;
import com.interagile.cliente.escola.model.dto.MateriaDTO;
import com.interagile.cliente.escola.repository.IMateriaRepository;

@Service
@CacheConfig(cacheNames = "Materias")
public class MateriaService implements IMateriaService {

	private IMateriaRepository materiaRepository;

	private ObjectMapper mapper;

	@Autowired
	public MateriaService(IMateriaRepository materiaRepository) {
		this.materiaRepository = materiaRepository;
		this.mapper = new ObjectMapper();
	}

	@Override
	public Boolean cadastrar(final MateriaCadastroModel materia) {

		try {
			this.validaCadastro(materia);
			MateriaDB materiaDao = new MateriaDB();
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

	@Cacheable(value = "consultar-materia", key = "#codMateria", unless = "#result == null")
	@Override
	public MateriaDTO consultarMateriaCadastrada(String codMateria) {
		try {
			final MateriaDB materiaDao = this.materiaRepository.findMateriaByCodigo(codMateria);
			if (materiaDao == null) {
				throw new CurriculoException("Matéria não encontrada", HttpStatus.BAD_REQUEST.value());
			}

			return this.mapper.convertValue(materiaDao, MateriaDTO.class);
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
			MateriaDB materiaDao = this.materiaRepository.findMateriaByCodigo(materia.getCodigo());
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
	public List<MateriaDTO> listar() {

		try {
			List<MateriaDB> materiaDao = this.materiaRepository.findAll();

			return this.mapper.convertValue(materiaDao, new TypeReference<List<MateriaDTO>>() {
			});
		} catch (Exception e) {
			throw e;
		}
	}

	public MateriaDB consultarMateriaDaoCadastrada(String codMateria) {
		try {
			final MateriaDB materiaDao = this.materiaRepository.findMateriaByCodigo(codMateria);
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
