package com.interagile.cliente.escola.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.interagile.cliente.escola.dao.MateriaDAO;
import com.interagile.cliente.escola.exception.MateriaException;
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
	public Boolean cadastro(final MateriaCadastroModel materia) {

		try {
			this.validaEntrada(materia);
			MateriaDAO materiaDao = new MateriaDAO();
			materiaDao.setNome(materia.getNome());
			materiaDao.setCodigo(materia.getCodigo());
			materiaDao.setFrequencia(materia.getFrequencia());
			materiaDao.setHoras(materia.getHoras());
			
			this.materiaRepository.save(materiaDao);
			return true;
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}

	}

	private void validaEntrada(MateriaCadastroModel materia) {
		if (materia.getNome() == null || StringUtils.isEmpty(materia.getNome())) {
			throw new MateriaException("Nome da matéria vazio.", HttpStatus.BAD_REQUEST.value());
		}
		if (materia.getCodigo() == null || StringUtils.isEmpty(materia.getCodigo())) {
			throw new MateriaException("Código da matéria vazio.", HttpStatus.BAD_REQUEST.value());
		}
		if (materia.getHoras() <= 0 ) {
			throw new MateriaException("Horas da máteria têm que ser positivas.", HttpStatus.BAD_REQUEST.value());
		}
		if (materia.getFrequencia()!= 1 && materia.getFrequencia()!= 2) {
			throw new MateriaException("Frequência pode ser apenas 1 ou 2.", HttpStatus.BAD_REQUEST.value());
		}
		if(this.materiaRepository.findMateriaByCodigo(materia.getCodigo())!=null) {
			throw new MateriaException("Código já foi usado para cadastro de outra matéria", HttpStatus.BAD_REQUEST.value());
		}

	}

}
